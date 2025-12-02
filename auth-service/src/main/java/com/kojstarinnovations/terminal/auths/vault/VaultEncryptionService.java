package com.kojstarinnovations.terminal.auths.vault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;
import org.springframework.vault.support.VaultTransitContext;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para cifrar/descifrar datos usando Vault Transit Engine
 */
@Service
public class VaultEncryptionService {

    private static final String TRANSIT_KEY_NAME = "mysql-app-data";
    private static final String TRANSIT_PATH = "transit";

    @Autowired
    private VaultTemplate vaultTemplate;

    /**
     * Cifra un texto plano usando Vault Transit
     *
     * @param plaintext Texto a cifrar
     * @return Texto cifrado en formato vault:v1:base64...
     */
    public String encrypt(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            return null;
        }

        try {
            // Convertir a bytes y luego a Base64
            byte[] bytes = plaintext.getBytes(StandardCharsets.UTF_8);
            String base64 = Base64.getEncoder().encodeToString(bytes);

            // Crear el plaintext para Vault
            Plaintext plaintextObj = Plaintext.of(base64);

            // Cifrar usando Vault
            String ciphertext = vaultTemplate
                    .opsForTransit(TRANSIT_PATH)
                    .encrypt(TRANSIT_KEY_NAME, plaintextObj)
                    .getCiphertext();

            return ciphertext;

        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar datos con Vault", e);
        }
    }

    /**
     * Descifra un texto cifrado por Vault Transit
     *
     * @param ciphertext Texto cifrado (formato: vault:v1:...)
     * @return Texto descifrado
     */
    public String decrypt(String ciphertext) {
        if (ciphertext == null || ciphertext.isEmpty()) {
            return null;
        }

        // Si no está cifrado (no tiene el prefijo vault:), retornar tal cual
        if (!ciphertext.startsWith("vault:")) {
            return ciphertext;
        }

        try {
            // Crear el objeto ciphertext
            Ciphertext ciphertextObj = Ciphertext.of(ciphertext);

            // Descifrar usando Vault
            Plaintext plaintext = vaultTemplate
                    .opsForTransit(TRANSIT_PATH)
                    .decrypt(TRANSIT_KEY_NAME, ciphertextObj);

            // Decodificar de Base64 a String
            byte[] decodedBytes = Base64.getDecoder().decode(plaintext.getPlaintext());
            return new String(decodedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar datos con Vault", e);
        }
    }

    /**
     * /**
     * Re-cifra datos con la última versión de la clave (después de rotación)
     *
     * @param ciphertext Texto cifrado con versión anterior
     * @return Texto cifrado con la última versión
     */
    public String rewrap(String ciphertext) {
        if (ciphertext == null || !ciphertext.startsWith("vault:")) {
            return ciphertext;
        }

        try {
            // Descifrar con la versión antigua
            String decrypted = decrypt(ciphertext);

            // Re-cifrar con la versión nueva
            String rewrapped = encrypt(decrypted);

            return rewrapped;

        } catch (Exception e) {
            throw new RuntimeException("Error al re-cifrar datos con Vault", e);
        }
    }

    /**
     * Verifica si Vault está disponible y configurado correctamente
     *
     * @return true si Vault está disponible
     */
    public boolean isVaultAvailable() {
        try {
            vaultTemplate.opsForTransit(TRANSIT_PATH).getKeys();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene información sobre la clave de cifrado
     *
     * @return Map con información de la clave
     */
    public Map<String, Object> getKeyInfo() {
        try {
            var keyInfo = vaultTemplate
                    .opsForTransit(TRANSIT_PATH)
                    .getKey(TRANSIT_KEY_NAME);

            Map<String, Object> info = new HashMap<>();
            info.put("name", keyInfo.getName());
            info.put("latestVersion", keyInfo.getLatestVersion());
            info.put("minDecryptionVersion", keyInfo.getMinDecryptionVersion());
            info.put("deletionAllowed", keyInfo.isDeletionAllowed());
            info.put("type", keyInfo.getType());

            return info;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener información de la clave", e);
        }
    }
}