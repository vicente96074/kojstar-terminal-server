package com.kojstarinnovations.terminal.auths.vault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for encrypting/decrypting data using Vault Transit Engine
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Service
@RequiredArgsConstructor
public class VaultEncryptionService {

    /**
     * Encrypt plain text using Vault Transit
     *
     * @param plaintext Text to encrypt
     * @return Ciphertext in vault:v1:base64... format
     */
    public String encrypt(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            return null;
        }

        try {
            // Convert to bytes and then to Base64
            byte[] bytes = plaintext.getBytes(StandardCharsets.UTF_8);
            String base64 = Base64.getEncoder().encodeToString(bytes);

            // Create the plaintext for Vault
            Plaintext plaintextObj = Plaintext.of(base64);

            // Encrypt using Vault
            return vaultTemplate.opsForTransit(TRANSIT_PATH).encrypt(TRANSIT_KEY_NAME, plaintextObj).getCiphertext();

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data with Vault", e);
        }
    }

    /**
     * Decrypt a text encrypted by Vault Transit
     *
     * @param ciphertext Ciphertext (format: vault:v1:...)
     * @return Decrypted text
     */
    public String decrypt(String ciphertext) {
        if (ciphertext == null || ciphertext.isEmpty()) {
            return null;
        }

        // If it is not encrypted (does not have the vault: prefix), return as is
        if (!ciphertext.startsWith("vault:")) {
            return ciphertext;
        }

        try {
            // Create the ciphertext object
            Ciphertext ciphertextObj = Ciphertext.of(ciphertext);

            // Decrypt using Vault
            Plaintext plaintext = vaultTemplate.opsForTransit(TRANSIT_PATH).decrypt(TRANSIT_KEY_NAME, ciphertextObj);

            // Decode from Base64 to String
            byte[] decodedBytes = Base64.getDecoder().decode(plaintext.getPlaintext());
            return new String(decodedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data with Vault", e);
        }
    }

    /**
     * Re-encrypts data with the latest key version (after rotation)
     *
     * @param ciphertext Ciphertext with the previous version
     * @return Ciphertext with the latest version
     */
    public String rewrap(String ciphertext) {
        if (ciphertext == null || !ciphertext.startsWith("vault:")) {
            return ciphertext;
        }

        try {
            // Decipher with the old version
            String decrypted = decrypt(ciphertext);
            return encrypt(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error re-encrypting data with Vault", e);
        }
    }

    /**
     * Checks if Vault is available and configured correctly
     *
     * @return true if Vault is available
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
     * Gets information about the encryption key
     *
     * @return Map with key information
     */
    public Map<String, Object> getKeyInfo() {
        try {
            var keyInfo = vaultTemplate.opsForTransit(TRANSIT_PATH).getKey(TRANSIT_KEY_NAME);

            Map<String, Object> info = new HashMap<>();
            assert keyInfo != null;
            info.put("name", keyInfo.getName());
            info.put("latestVersion", keyInfo.getLatestVersion());
            info.put("minDecryptionVersion", keyInfo.getMinDecryptionVersion());
            info.put("deletionAllowed", keyInfo.isDeletionAllowed());
            info.put("type", keyInfo.getType());

            return info;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving key information", e);
        }
    }

    private static final String TRANSIT_KEY_NAME = "mysql-app-data";
    private static final String TRANSIT_PATH = "transit";
    private final VaultTemplate vaultTemplate;
}