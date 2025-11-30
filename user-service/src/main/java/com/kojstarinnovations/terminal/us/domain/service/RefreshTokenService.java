package com.kojstarinnovations.terminal.us.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.RefreshTokenData;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.TwoFactorCode;
import com.kojstarinnovations.terminal.commons.exception.CriticalSecurityException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.kojstarinnovations.terminal.commons.data.constants.RedisConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    public void addIPToToken(String tokenId, String ip) {
        String tokenIpKey = PREFIX_TOKEN_IP + tokenId;
        List<String> tokenIps = redisTemplate.opsForList().range(tokenIpKey, 0, -1);
        if (tokenIps == null || !tokenIps.contains(ip)) {
            redisTemplate.opsForList().rightPush(tokenIpKey, ip);
        }
    }

    @SneakyThrows
    public void updateUserAgentIntoToken(String tokenId, String userAgent) {
        String key = PREFIX_REFRESH_TOKEN + tokenId;
        RefreshTokenData tokenData = getRefreshToken(tokenId);
        tokenData.setUserAgent(userAgent);
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(tokenData));
        log.info("Updated user agent for tokenId: {}", tokenId);
    }

    /**
     * Method to delete a refresh token from Redis
     *
     * @param code code sent to the user
     * @param sub  the sub of the user
     */
    @SneakyThrows
    public void storeCodeSentToSmsBySub(TwoFactorCode code, String sub) {
        String key = PREFIX_SMS + sub;
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(code), Duration.between(code.getIssuedAt(), code.getExpiration()));
        log.info("Stored code sent to SMS for sub: {} with code: {}", sub, code.getCode());
    }

    /**
     * Get a code sent to the user by SMS
     *
     * @param sub the sub of the user
     * @return TwoFactorCode
     */
    @SneakyThrows
    public TwoFactorCode getCodeSentToSmsBySub(String sub) {
        String key = PREFIX_SMS + sub;
        String jsonValue = redisTemplate.opsForValue().get(key);

        if (jsonValue == null) {
            return null;
        }

        return objectMapper.readValue(jsonValue, TwoFactorCode.class);
    }

    /**
     * Method to delete a refresh token from Redis
     *
     * @param code code sent to the user
     * @param sub  the sub of the user
     */
    @SneakyThrows
    public void storeCodeSentToEmailBySub(TwoFactorCode code, String sub) {
        String key = PREFIX_EMAIL + sub;
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(code), Duration.between(code.getIssuedAt(), code.getExpiration()));
        log.info("Stored code sent to email for sub: {} with code: {}", sub, code.getCode());
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

    public Integer getTriesBySub(String sub) {
        String triesKey = PREFIX_ATTEMPTS_BY_SUB + sub;
        String tries = redisTemplate.opsForValue().get(triesKey);
        return tries != null ? Integer.parseInt(tries) : 0;
    }

    public void addOrUpdateAttemptsBySubAndIP(String sub, String ip) {
        String triesKey = PREFIX_ATTEMPTS_BY_SUB + sub;
        Integer tries = getTriesBySub(sub);
        if (tries == null) {
            redisTemplate.opsForValue().set(triesKey, "1");
        } else {
            redisTemplate.opsForValue().increment(triesKey);
        }

        tries = getTriesBySub(sub);
        // Si el número de intentos supera el máximo se guarda el ip como sospechoso
        if (tries != null && tries >= MAX_ATTEMPTS_BY_TWO_FACTOR) {
            addSuspiciousIp(sub, ip);
            // se bloquea el acceso al usuario
            this.userService.blockUser(sub);
            throw new CriticalSecurityException(ExceptionConstants.SEVERAL_FAILED_TWO_FACTOR_ATTEMPTS);
        }
    }

    public void addOrUpdateAttemptsBySubAndUserAgent(String sub, String userAgent) {
        String triesKey = PREFIX_ATTEMPTS_BY_SUB + sub;
        Integer tries = getTriesBySub(sub);
        if (tries == null) {
            redisTemplate.opsForValue().set(triesKey, "1");
        } else {
            redisTemplate.opsForValue().increment(triesKey);
        }

        tries = getTriesBySub(sub);
        // Si el número de intentos supera el máximo se guarda el user agent como sospechoso
        if (tries != null && tries >= MAX_ATTEMPTS_BY_TWO_FACTOR) {
            addSuspiciousUserAgent(sub, userAgent);
            // se bloquea el acceso al usuario
            this.userService.blockUser(sub);
            throw new CriticalSecurityException(ExceptionConstants.SEVERAL_FAILED_TWO_FACTOR_ATTEMPTS);
        }
    }

    public List<String> getSuspiciousIpsBySub(String sub) {
        String suspiciousIpKey = PREFIX_SUSPICIOUS_IP + sub;
        List<String> suspiciousIps = redisTemplate.opsForList().range(suspiciousIpKey, 0, -1);
        return suspiciousIps != null ? suspiciousIps : List.of();
    }

    public void addSuspiciousIp(String sub, String ip) {
        String suspiciousIpKey = PREFIX_SUSPICIOUS_IP + sub;
        List<String> suspiciousIps = redisTemplate.opsForList().range(suspiciousIpKey, 0, -1);
        if (suspiciousIps == null || !suspiciousIps.contains(ip)) {
            redisTemplate.opsForList().rightPush(suspiciousIpKey, ip);
        }
    }

    public List<String> getSuspiciousUserAgentsBySub(String sub) {
        String suspiciousUserAgentKey = PREFIX_SUSPICIOUS_USER_AGENT + sub;
        List<String> suspiciousUserAgents = redisTemplate.opsForList().range(suspiciousUserAgentKey, 0, -1);
        return suspiciousUserAgents != null ? suspiciousUserAgents : new ArrayList<>();
    }

    public void addSuspiciousUserAgent(String sub, String userAgent) {
        String suspiciousUserAgentKey = PREFIX_SUSPICIOUS_USER_AGENT + sub;
        List<String> suspiciousUserAgents = redisTemplate.opsForList().range(suspiciousUserAgentKey, 0, -1);
        if (suspiciousUserAgents == null || !suspiciousUserAgents.contains(userAgent)) {
            redisTemplate.opsForList().rightPush(suspiciousUserAgentKey, userAgent);
        }
    }

    /**
     * Get claim from token
     *
     * @param token String token
     * @param claim String claim
     * @return String claim
     */
    public String getClaimFromToken(String token, String claim) {
        return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().get(claim, String.class);
    }

    /**
     * Method to get secret
     *
     * @param secret String secret
     * @return Key object
     */
    private Key getSecret(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public void resetAttemptsBySub(String sub) {
        String triesKey = PREFIX_ATTEMPTS_BY_SUB + sub;
        redisTemplate.delete(triesKey);
    }

    @Value("${jwt.secret}")
    private String secret;
    private final UserService userService;

    public void addOrUpdateAttemptsBySub(String sub) {
        String triesKey = PREFIX_ATTEMPTS_BY_SUB + sub;
        Integer tries = getTriesBySub(sub);
        if (tries == null) {
            redisTemplate.opsForValue().set(triesKey, "1");
        } else {
            redisTemplate.opsForValue().increment(triesKey);
        }

        tries = getTriesBySub(sub);
        // Si el número de intentos supera el máximo se guarda el user agent como sospechoso
        if (tries != null && tries >= MAX_ATTEMPTS_BY_TWO_FACTOR) {

            // se bloquea el acceso al usuario
            this.userService.blockUser(sub);
            throw new CriticalSecurityException(ExceptionConstants.SEVERAL_FAILED_TWO_FACTOR_ATTEMPTS);
        }
    }

    public void removeCodeSentToSmsBySub(String sub) {
        String key = PREFIX_SMS + sub;
        redisTemplate.delete(key);
        log.info("Removed code sent to SMS for sub: {}", sub);
    }
}