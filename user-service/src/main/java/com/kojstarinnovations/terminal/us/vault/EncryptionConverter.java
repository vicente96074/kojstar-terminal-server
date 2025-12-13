package com.kojstarinnovations.terminal.us.vault;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

/**
 * JPA converter that automatically encrypts/decrypts database fields
 * <p>
 * Usage in entities:
 *
 * @Convert(converter = EncryptionConverter.class)
 * private String sensitiveField;
 */
@Converter
@Component
public class EncryptionConverter implements AttributeConverter<String, String> {

    private VaultEncryptionService getService() {
        // Statically calls the context reference to obtain the bean
        return SpringContext.getBean(VaultEncryptionService.class);
    }

    /**
     * Converts the entity value (plaintext) to a database value (ciphertext)
     * Executes before INSERT/UPDATE
     */
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }

        // If it's already encrypted, don't encrypt again
        if (attribute.startsWith("vault:")) {
            return attribute;
        }

        return getService().encrypt(attribute);
    }

    /**
     * Converts the database value (ciphertext) to the entity value (plaintext)
     * Executes after SELECT
     */
    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        // If it's not encrypted, return as is (for gradual migration)
        if (!dbData.startsWith("vault:")) {
            return dbData;
        }

        return getService().decrypt(dbData);
    }
}