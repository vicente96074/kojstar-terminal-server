package com.kojstarinnovations.terminal.auths.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.RefreshTokenData;
import com.kojstarinnovations.terminal.commons.exception.CriticalSecurityException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.kojstarinnovations.terminal.commons.data.constants.RedisConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Method to store a refresh token in Redis
     *
     * @param tokenData  RefreshTokenData
     * @param ip         String ip
     * @param now        Instant now
     * @param expiration Instant expiration
     */
    @SneakyThrows
    public void storeRefreshToken(RefreshTokenData tokenData, String ip, Instant now, Instant expiration) {

        // Guardar el token como JSON en su clave propia
        redisTemplate.opsForValue().set((PREFIX_REFRESH_TOKEN + tokenData.getTokenId()),
                objectMapper.writeValueAsString(tokenData),
                Duration.between(now, expiration)
        );

        // Asociar tokenId con userId en una lista o set
        String subKey = PREFIX_REFRESH_TOKEN + tokenData.getSub();
        List<String> tokenIds = redisTemplate.opsForList().range(subKey, 0, -1);
        if (tokenIds == null || !tokenIds.contains(tokenData.getTokenId())) {
            redisTemplate.opsForList().rightPush(subKey, tokenData.getTokenId());
        }

        // Asociar el ip con el tokenId en una lista o set
        String tokenIpKey = PREFIX_TOKEN_IP + tokenData.getTokenId();
        List<String> tokenIps = redisTemplate.opsForList().range(tokenIpKey, 0, -1);
        if (tokenIps == null || !tokenIps.contains(ip)) {
            redisTemplate.opsForList().rightPush(tokenIpKey, ip);
        }

        log.info("Stored refresh token for sub: {} with tokenId: {}", tokenData.getSub(), tokenData.getTokenId());
    }

    /**
     * Method to get the refresh token from Redis
     *
     * @param tokenId String tokenId
     * @return RefreshTokenData
     */
    @SneakyThrows
    public RefreshTokenData getRefreshToken(String tokenId) {
        String tokenKey = PREFIX_REFRESH_TOKEN + tokenId;
        String jsonValue = redisTemplate.opsForValue().get(tokenKey);
        if (jsonValue == null) {
            throw new CriticalSecurityException(ExceptionConstants.TOKEN_NOT_FOUND);
        }
        return objectMapper.readValue(jsonValue, RefreshTokenData.class);
    }

    /**
     * Method to clean expired tokens for all users
     */
    @Scheduled(cron = "0 0 0 ? * MON") // Cada lunes a las 00:00
    public void cleanExpiredTokensForAllUsers() {
        String pattern = "refresh_token_user:*";
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(100).build();

        try {
            assert redisTemplate.getConnectionFactory() != null;
            try (Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options)) {
                while (cursor.hasNext()) {
                    String userKey = new String(cursor.next());
                    String sub = userKey.replace("refresh_token_user:", "");
                    cleanExpiredTokensForSub(sub);
                }
            }
        } catch (Exception e) {
            log.error("Error during scheduled cleanup of expired tokens", e);
        }
    }

    /**
     * Method to clean expired tokens for a specific user
     *
     * @param sub String sub
     */
    public void cleanExpiredTokensForSub(String sub) {
        String userKey = PREFIX_SUB_TOKENS + sub;
        List<String> tokenIds = redisTemplate.opsForList().range(userKey, 0, -1);
        if (tokenIds == null || tokenIds.isEmpty()) return;

        for (String tokenId : tokenIds) {
            String tokenKey = PREFIX_REFRESH_TOKEN + tokenId;
            Boolean exists = redisTemplate.hasKey(tokenKey);

            if (!exists) {
                redisTemplate.opsForList().remove(userKey, 0, tokenId);
                log.info("Removed expired tokenId {} from sub {}", tokenId, sub);
            }
        }
    }

    /**
     * Method to revoke a refresh token
     *
     * @param tokenId String tokenId
     */
    public void revokeRefreshToken(String tokenId) {
        String key = PREFIX_REFRESH_TOKEN + tokenId;
        redisTemplate.delete(key);
    }

    /**
     * Method to revoke all refresh tokens for a user
     *
     * @param tokenId String tokenId
     * @return List<String> List of tokenIds
     */
    public List<String> getTokenIpsByTokenId(String tokenId) {
        String tokenIpKey = PREFIX_TOKEN_IP + tokenId;
        List<String> tokenIps = redisTemplate.opsForList().range(tokenIpKey, 0, -1);
        if (tokenIps == null || tokenIps.isEmpty()) {
            throw new CriticalSecurityException(ExceptionConstants.TOKEN_IP_NOT_FOUND);
        }
        return tokenIps;
    }

    /**
     * Method to get all refresh tokens for a user
     *
     * @param sub String sub
     * @return List<String> List of tokenIds
     */
    public List<String> getSuspiciousIpsBySub(String sub) {
        String suspiciousIpKey = PREFIX_SUSPICIOUS_IP + sub;
        List<String> suspiciousIps = redisTemplate.opsForList().range(suspiciousIpKey, 0, -1);
        return suspiciousIps != null ? suspiciousIps : new ArrayList<>();
    }

    /**
     * Method to get all refresh tokens for a user
     *
     * @param sub String sub
     * @return List<String> List of tokenIds
     */
    public List<String> getSuspiciousUserAgentsBySub(String sub) {
        String suspiciousUserAgentKey = PREFIX_SUSPICIOUS_USER_AGENT + sub;
        List<String> suspiciousUserAgents = redisTemplate.opsForList().range(suspiciousUserAgentKey, 0, -1);
        return suspiciousUserAgents != null ? suspiciousUserAgents : new ArrayList<>();
    }
}