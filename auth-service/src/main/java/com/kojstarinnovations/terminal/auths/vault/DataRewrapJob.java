package com.kojstarinnovations.terminal.auths.vault;

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
 * Job para re-cifrar automáticamente datos después de rotación de claves en Vault
 * <p>
 * Detecta automáticamente todas las entidades JPA que tienen campos con @Convert(converter = EncryptionConverter.class)
 * y los re-cifra con la última versión de la clave de Vault.
 *
 * @Author: Kojstar Innovations
 */
//@Component
@Slf4j
@RequiredArgsConstructor
@Service
@Component
public class DataRewrapJob {

    @PersistenceContext
    private EntityManager entityManager;
    private final VaultEncryptionService vaultService;

    /**
     * Ejecuta el re-cifrado de forma manual
     *
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void rewrapAllEncryptedData() {
        log.info("Iniciando proceso de re-cifrado de datos...");
        long startTime = System.currentTimeMillis();

        try {
            // 1. Obtener todas las entidades con campos cifrados
            List<Class<?>> encryptedEntities = findEntitiesWithEncryptedFields();

            // 2. Re-cifrar cada entidad
            for (Class<?> entityClass : encryptedEntities) {
                try {
                    rewrapEntity(entityClass);
                    log.info("Entity {} rewrapped successfully", entityClass.getName());
                } catch (Exception e) {
                    log.error("❌ Error al re-cifrar entidad {}: {}",
                            entityClass.getSimpleName(), e.getMessage());
                }
            }

            long duration = System.currentTimeMillis() - startTime;

            log.info("✅ Re-cifrado completado en {}ms", duration);

        } catch (Exception e) {
            log.error("❌ Error fatal en el proceso de re-cifrado", e);
        }
    }

    /**
     * Encuentra todas las entidades JPA que tienen campos con @Convert(EncryptionConverter)
     */
    private List<Class<?>> findEntitiesWithEncryptedFields() {
        Set<Class<?>> encryptedEntities = new HashSet<>();

        // Obtener todas las entidades registradas en el EntityManager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        for (EntityType<?> entity : entities) {
            Class<?> entityClass = entity.getJavaType();

            // Verificar si tiene campos cifrados
            if (hasEncryptedFields(entityClass)) {
                encryptedEntities.add(entityClass);
            }
        }

        return new ArrayList<>(encryptedEntities);
    }

    /**
     * Verifica si una entidad tiene campos con @Convert(EncryptionConverter)
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
     * Obtiene todos los campos de una clase (incluyendo heredados)
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
     * Re-cifra todos los registros de una entidad específica
     */
    @Transactional
    public void rewrapEntity(Class<?> entityClass) {

        try {
            // 1. Obtener nombre de la entidad
            String entityName = entityClass.getSimpleName();
            Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
            if (entityAnnotation != null && !entityAnnotation.name().isEmpty()) {
                entityName = entityAnnotation.name();
            }

            // 2. Obtener todos los registros
            String queryStr = "SELECT e FROM " + entityName + " e";
            List<?> entities = entityManager.createQuery(queryStr).getResultList();

            if (entities.isEmpty()) {
                log.info("Entity {} no encontrado", entityName);
                return;
            }

            // 3. Obtener campos cifrados
            List<Field> encryptedFields = getEncryptedFields(entityClass);

            // 4. Re-cifrar cada registro
            for (Object entity : entities) {
                boolean entityModified = false;

                for (Field field : encryptedFields) {
                    field.setAccessible(true);

                    try {
                        String currentValue = (String) field.get(entity);
                        log.info("Current value {}", currentValue);

                        // Solo re-cifrar si el valor está cifrado
                        if (currentValue != null && currentValue.startsWith("vault:")) {
                            //log.info("Entity {} encontrado", entityName);
                            String rewrapped = vaultService.rewrap(currentValue);

                            // Solo actualizar si cambió la versión
                            if (!currentValue.equals(rewrapped)) {
                                field.set(entity, rewrapped);
                                entityModified = true;
                            }
                        }

                        /*// Cifrar valores existentes que no han sido cifrados
                        if (currentValue != null && !(currentValue.startsWith("vault:"))) {
                            log.info("Entity not encrypted field {} encontrado", entityName);

                            String encryptedValue = vaultService.encrypt(currentValue);
                            if (!currentValue.equals(encryptedValue)) {
                                field.set(entity, encryptedValue);
                                entityModified = false;
                            }
                        }*/

                    } catch (Exception e) {
                        log.error("❌ Error al re-cifrar campo {} en {}: {}",
                                field.getName(), entityName, e.getMessage());
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
            e.printStackTrace();
            log.error("Exception: {}", String.valueOf(e));
            throw new RuntimeException("Error en rewrap de " + entityClass.getSimpleName(), e);
        }
    }

    /**
     * Obtiene los campos de una entidad que usan EncryptionConverter
     */
    private List<Field> getEncryptedFields(Class<?> entityClass) {
        return getAllFields(entityClass).stream()
                .filter(field -> {
                    Convert convert = field.getAnnotation(Convert.class);
                    return convert != null && convert.converter() == EncryptionConverter.class;
                })
                .collect(Collectors.toList());
    }
}