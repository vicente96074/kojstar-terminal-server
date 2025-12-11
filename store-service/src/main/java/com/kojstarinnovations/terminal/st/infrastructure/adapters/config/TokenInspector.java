package com.kojstarinnovations.terminal.st.infrastructure.adapters.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kojstarinnovations.terminal.commons.data.constants.I18nUserConstants;
import com.kojstarinnovations.terminal.commons.exception.UnauthorizedException;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public static Map<String, Object> extractClaimsWithoutValidation(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException(I18nUserConstants.EXCEPTION_TI_INVALID_TOKEN_FORMAT);
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payload, Map.class);
        } catch (Exception e) {
            throw new UnauthorizedException(I18nUserConstants.EXCEPTION_TI_INVALID_TOKEN_FORMAT);
        }
    }
}
