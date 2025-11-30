package com.kojstarinnovations.terminal.commons.data.dto.authservice;

import lombok.Builder;

/**
 * JwtDTO - Data Transfer Object for JWT
 *
 * @param accessToken
 * @param refreshToken
 */
@Builder
public record JwtDTO(
        String accessToken,
        String refreshToken
) {
}