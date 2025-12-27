package com.kojstarinnovations.terminal.auths.jwt;


import org.springframework.security.core.Authentication;

import java.time.Instant;

/**
 * JwtService - This interface is responsible for generating and validating JWT tokens
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface JwtService {
    String generateAccessToken(Authentication authentication);

    String getClaimFromToken(String token, String claim);

    String generateRefreshToken(Authentication authentication, Instant now);

    boolean refreshTokenNotValid(String refreshToken);

    String generateInternalToken();
}