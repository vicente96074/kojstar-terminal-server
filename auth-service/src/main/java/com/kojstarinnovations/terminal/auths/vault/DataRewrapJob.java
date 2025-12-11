package com.kojstarinnovations.terminal.auths.vault;

import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.exception.VaultException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Job to automatically re-encrypt data after key rotation in Vault
 * <p>
 * Automatically detects all JPA entities that have fields with @Convert(converter = EncryptionConverter Class)
 * and re-encrypts them with the latest version of the Vault key.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Component
public class DataRewrapJob {

    /**
     * Performs manual re-encryption
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void rewrapAllEncryptedData() {
        log.info("Starting data re-encryption process...");
        long startTime = System.currentTimeMillis();

        try {
            // 1. Obtain all entities with encrypted fields
            List<Class<?>> encryptedEntities = findEntitiesWithEncryptedFields();

            // 2. Re-encrypt each entity
            for (Class<?> entityClass : encryptedEntities) {
                try {
                    rewrapEntity(entityClass);
                    log.info("Entity {} rewrapped successfully", entityClass.getName());
                } catch (Exception e) {
                    log.error("❌ Error re-encrypting entity {}: {}", entityClass.getSimpleName(), e.getMessage());
                }
            }

            long duration = System.currentTimeMillis() - startTime;

            log.info("✅ Re-encryption completed in {}ms", duration);

        } catch (Exception e) {
            log.error("❌ Fatal error in the re-encryption process", e);
            throw new VaultException(I18nAuthConstants.EXCEPTION_REWRAP_ALL_ENTITY_EXCEPTION);
        }
    }

    /**
     * Find all JPA entities that have fields with @Convert(EncryptionConverter)
     */
    private List<Class<?>> findEntitiesWithEncryptedFields() {
        Set<Class<?>> encryptedEntities = new HashSet<>();

        // Get all entities registered in the EntityManager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        for (EntityType<?> entity : entities) {
            Class<?> entityClass = entity.getJavaType();

            // Check if it has encrypted fields
            if (hasEncryptedFields(entityClass)) {
                encryptedEntities.add(entityClass);
            }
        }

        return new ArrayList<>(encryptedEntities);
    }

    /**
     * Checks if an entity has fields with @Convert(EncryptionConverter)
     */
    private boolean hasEncryptedFields(Class<?> entityClass) {
        for (Field field : getAllFields(entityClass)) {
            Convert convertAnnotation = field.getAnnotation(Convert.class);
            if (convertAnnotation != null &&
                    convertAnnotation.converter() == EncryptionConverter.class) {
                log.info("Field: {} has encrypt field {}", field.getName(), true);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all fields of a class (including inherited ones)
     */
    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = clazz;

        while (current != null && current != Object.class) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }

        return fields;
    }

    /**
     * Re-encrypts all records for a specific entity
     */
    @Transactional
    public void rewrapEntity(Class<?> entityClass) {

        try {
            // 1. Obtain entity name
            String entityName = entityClass.getSimpleName();
            Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
            if (entityAnnotation != null && !entityAnnotation.name().isEmpty()) {
                entityName = entityAnnotation.name();
            }

            // 2. Get all records
            String queryStr = "SELECT e FROM " + entityName + " e";
            List<?> entities = entityManager.createQuery(queryStr).getResultList();

            if (entities.isEmpty()) {
                log.info("Entity {} not found", entityName);
                return;
            }

            // 3. Obtain encrypted fields
            List<Field> encryptedFields = getEncryptedFields(entityClass);

            // 4. Re-encrypt each record
            for (Object entity : entities) {
                boolean entityModified = false;

                for (Field field : encryptedFields) {
                    field.setAccessible(true);

                    try {
                        String currentValue = (String) field.get(entity);
                        log.info("Current value {}", currentValue);

                        // Only re-encrypt if the value is encrypted
                        if (currentValue != null && currentValue.startsWith("vault:")) {
                            String rewrapped = vaultService.rewrap(currentValue);

                            // Only update if the version has changed
                            if (!currentValue.equals(rewrapped)) {
                                field.set(entity, rewrapped);
                                entityModified = true;
                            }
                        }

                    } catch (Exception e) {
                        log.error("❌ Error re-encrypting field {} in {}: {}", field.getName(), entityName, e.getMessage());
                    }
                }

                if (entityModified) {
                    log.info("Entity merge");
                    entityManager.merge(entity);
                }
            }

            // 5. Flush cambios
            entityManager.flush();
        } catch (Exception e) {
            log.error("Exception: {}", String.valueOf(e));
            throw new RuntimeException("Exception: " + e.getMessage());
        }
    }

    /**
     * Gets the fields of an entity that use EncryptionConverter
     */
    private List<Field> getEncryptedFields(Class<?> entityClass) {
        return getAllFields(entityClass).stream()
                .filter(field -> {
                    Convert convert = field.getAnnotation(Convert.class);
                    return convert != null && convert.converter() == EncryptionConverter.class;
                })
                .collect(Collectors.toList());
    }

    @PersistenceContext
    private EntityManager entityManager;
    private final VaultEncryptionService vaultService;
}