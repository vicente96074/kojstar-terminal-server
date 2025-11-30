package com.kojstarinnovations.terminal.us.infrastructure.adapters.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Map;

/**
 * TokenInspector - Token Inspector, responsible for extracting the claims from a JWT token
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
public class TokenInspector {

    /**
     * Extract Claims Without Validation
     *
     * @param token String
     * @return Map<String, Object>
     */
    public static Map<String, Object> extractClaimsWithoutValidation(String token) {
        try {
            //log.info("Token recibido: {}", token);

            // Dividir el token en sus tres partes
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("El token no tiene el formato esperado (header.payload.signature).");
            }

            // Decodificar el payload (segunda parte del token)
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            //log.info("Payload decodificado: {}", payload);

            // Convertir el payload JSON a un Map
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payload, Map.class);
        } catch (Exception e) {
            log.error("Error al extraer los claims del token.", e);
            throw new IllegalArgumentException("No se pudieron extraer los claims del token.", e);
        }
    }
}
