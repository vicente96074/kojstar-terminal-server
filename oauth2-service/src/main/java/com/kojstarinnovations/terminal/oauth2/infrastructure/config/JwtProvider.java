package com.kojstarinnovations.terminal.oauth2.infrastructure.config;

import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.Methods;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

/**
 * JwtProvider - This class is used to generate, validate and get data from JWT tokens
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
public class JwtProvider {

    /**
     * Generate internal token
     *
     * @return String token
     */
    public String generateInternalToken() {
        return Jwts.builder().setSubject("internal")
                .claim("settingId", "internal")
                .claim("roles", List.of("INTERNAL"))
                .claim("accesses", List.of("INTERNAL"))
                .claim(String.valueOf(Methods.AUTHENTICATION_METHOD).toLowerCase(), AuthenticationMethod.INTERNAL.name().toLowerCase())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 5 * 60L * 1000L)) // Expire in 3 minutes
                .signWith(getSecret(secret)).compact();
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

    @Value("${jwt.secret}")
    private String secret;
}