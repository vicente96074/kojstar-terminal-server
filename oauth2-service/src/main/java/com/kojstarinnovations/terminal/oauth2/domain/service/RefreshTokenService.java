package com.kojstarinnovations.terminal.oauth2.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    public void storeRefreshToken(String tokenId, String token, Instant now, Instant expirationDays) {
        String key = PREFIX + tokenId;
        redisTemplate.opsForValue().set(key, token, Duration.between(now, expirationDays));
        log.info("Stored refresh token for tokenId: {}", tokenId);
    }

    public boolean isRefreshTokenValid(String tokenId, String token) {
        String key = PREFIX + tokenId;
        String storedToken = redisTemplate.opsForValue().get(key);
        if (storedToken == null) {
            log.warn("No refresh token found for tokenId: {}", tokenId);
            return false;
        }

        boolean isRefreshValid = token.equals(storedToken);
        log.info("Refresh token valid: {}", isRefreshValid);
        return isRefreshValid;
    }

    public void revokeRefreshToken(String tokenId) {
        String key = PREFIX + tokenId;
        redisTemplate.delete(key);
        log.info("Revoked refresh token for tokenId: {}", tokenId);
    }

    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "refresh_token:";
}