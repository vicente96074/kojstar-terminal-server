package com.kojstarinnovations.terminal.commons.data.transport.authservice;

import lombok.Builder;

/**
 * RefreshTokenRequest - Data Transfer Object for Refresh Token Request
 *
 * @param refreshToken - String
 */
@Builder
public record RefreshTokenRequest(
        String refreshToken
) {
}