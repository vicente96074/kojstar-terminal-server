package com.kojstarinnovations.terminal.auths.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.auths.vault.DataRewrapJob;
import com.kojstarinnovations.terminal.auths.vault.VaultEncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vault")
@RequiredArgsConstructor
public class VaultTestController {

    private final VaultEncryptionService vaultService;

    /**
     * Verifica la conexión con Vault
     * GET /api/vault/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkVaultHealth() {
        Map<String, Object> response = new HashMap<>();

        boolean available = vaultService.isVaultAvailable();
        response.put("vaultAvailable", available);

        if (available) {
            response.put("keyInfo", vaultService.getKeyInfo());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Prueba de cifrado/descifrado
     * POST /api/vault/test
     * Body: { "text": "Hola Mundo" }
     */
    @PostMapping("/test")
    public ResponseEntity<Map<String, String>> testEncryption(@RequestBody Map<String, String> request) {
        String plaintext = request.get("text");

        // Cifrar
        String encrypted = vaultService.encrypt(plaintext);

        // Descifrar
        String decrypted = vaultService.decrypt(encrypted);

        Map<String, String> response = new HashMap<>();
        response.put("original", plaintext);
        response.put("encrypted", encrypted);
        response.put("decrypted", decrypted);
        response.put("match", plaintext.equals(decrypted) ? "✅ Coinciden" : "❌ No coinciden");

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/rewrap", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rewrapVault() {
        dataRewrapJob.rewrapAllEncryptedData();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Success");
        return ResponseEntity.ok(response);
    }


    private final DataRewrapJob dataRewrapJob;
}
