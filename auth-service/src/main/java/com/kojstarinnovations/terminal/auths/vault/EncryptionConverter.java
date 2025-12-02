package com.kojstarinnovations.terminal.auths.vault;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Conversor JPA que cifra/descifra automáticamente los campos de la base de datos
 *
 * Uso en entidades:
 * @Convert(converter = EncryptionConverter.class)
 * private String campoSensible;
 */
@Converter
@Component
public class EncryptionConverter implements AttributeConverter<String, String> {

    private static VaultEncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(VaultEncryptionService service) {
        EncryptionConverter.encryptionService = service;
    }

    /**
     * Convierte el valor de la entidad (plaintext) a valor de base de datos (ciphertext)
     * Se ejecuta antes de INSERT/UPDATE
     */
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }

        // Si ya está cifrado, no cifrar de nuevo
        if (attribute.startsWith("vault:")) {
            return attribute;
        }

        return encryptionService.encrypt(attribute);
    }

    /**
     * Convierte el valor de la base de datos (ciphertext) a valor de la entidad (plaintext)
     * Se ejecuta después de SELECT
     */
    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        // Si no está cifrado, retornar tal cual (para migración gradual)
        if (!dbData.startsWith("vault:")) {
            return dbData;
        }

        return encryptionService.decrypt(dbData);
    }
}