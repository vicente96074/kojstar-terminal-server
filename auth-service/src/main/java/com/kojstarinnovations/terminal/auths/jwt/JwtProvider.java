package com.kojstarinnovations.terminal.auths.jwt;

import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.Methods;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * JwtProvider - This class is used to generate, validate and get data from JWT tokens
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements JwtService {

    /**
     * Generate refresh token
     *
     * @param authentication Authentication object with user data
     * @return String token
     */
    public String generateRefreshToken(Authentication authentication, LocalDateTime createdAt) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        return Jwts.builder()
                .claim("token_id", UUIDHelper.generateUUID("rft", 15))
                .claim("sub", principalUser.getSub())
                .claim("createdAt", createdAt.toString())
                .claim("issuer", "kojnexus")
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + (jwtExpiration * 60L * 1000L))) // Expire in 30 days (milliseconds)
                .signWith(getSecret(secret)).compact();
    }

    /**
     * Generate access token
     *
     * @param authentication Authentication object with user data
     * @return String token
     */
    @Override
    public String generateAccessToken(Authentication authentication) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        List<String> roles = principalUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<String> accesses = principalUser.getAccesses().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder().setSubject(principalUser.getUsername())
                .claim("sub", principalUser.getSub())
                .claim("roles", roles)
                .claim("accesses", accesses)
                .claim("storeBranchId", principalUser.getStoreBranchId())
                .claim("storeId", principalUser.getStoreId())
                .claim("provider", "kojnexus")
                .claim(String.valueOf(Methods.AUTHENTICATION_METHOD).toLowerCase(), AuthenticationMethod.CUSTOM.name().toLowerCase())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1 * 60L * 1000L)) // Expire in 1 minutes
                .signWith(getSecret(secret)).compact();
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
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 3 * 60L * 1000L)) // Expire in 3 minutes
                .signWith(getSecret(secret)).compact();
    }

    /**
     * Get username from token
     *
     * @param token String token
     * @return String username
     */
    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Method to validate token
     *
     * @param token String token
     * @return boolean
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Logger.getLogger("Validando token").info("Token: " + token);
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            Logger.getLogger("Malformed JWT Token").log(Level.SEVERE, "Token mal formado {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            Logger.getLogger("Unsupported JWT Token").log(Level.SEVERE, "Token no soportado {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            Logger.getLogger("Expired JWT Token").log(Level.SEVERE, "Token expirado {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger("JWT claims vacío").log(Level.SEVERE, "JWT claims vacío {}", ex.getMessage());
        } catch (SignatureException ex) {
            Logger.getLogger("JWT signature fail").log(Level.SEVERE, "JWT signature fallo {}", ex.getMessage());
        }

        return false;
    }

    @SneakyThrows
    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(refreshToken);
            return true;
        } catch (MalformedJwtException ex) {
            Logger.getLogger("Malformed JWT Token").log(Level.SEVERE, "Token mal formado {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            Logger.getLogger("Unsupported JWT Token").log(Level.SEVERE, "Token no soportado {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            Logger.getLogger("Expired JWT Token").log(Level.SEVERE, "Token expirado {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger("JWT claims vacío").log(Level.SEVERE, "JWT claims vacío {}", ex.getMessage());
        } catch (SignatureException ex) {
            Logger.getLogger("JWT signature fail").log(Level.SEVERE, "JWT signature fallo {}", ex.getMessage());
        }

        return false;
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

    @Value("${jwt.expiration}")
    private int jwtExpiration;
}