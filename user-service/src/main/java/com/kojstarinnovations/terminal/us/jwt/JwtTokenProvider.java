package com.kojstarinnovations.terminal.us.jwt;

import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.Methods;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtTokenProvider - Class to provide the methods to validate a token and get the authentication from it
 * Also, it has a method to resolve the token from the request
 * It implements JwtService to provide the methods
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
public class JwtTokenProvider implements JwtService {

    /**
     * validateToken method
     *
     * @param token the token to be validated
     * @return a boolean value to indicate if the token is valid or not
     */
    @Override
    public boolean validateToken(String token) {
        try {
            //Logger.getLogger("Validando token").info("Token: " + token);
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Malformed JWT Token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT Token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT Token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex.getMessage());
        } catch (SignatureException ex) {
            log.error("JWT signature fail: {}", ex.getMessage());
        }
        return false;
    }

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
     * resolveToken method
     *
     * @param request the request to resolve the token from
     * @return the token resolved
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extrae el token, que sigue a "Bearer "
        }
        return null;
    }

    /**
     * getClaimsFromToken method
     *
     * @param token the token to get the claims from
     * @return the claims object
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecret(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * getAuthentication method
     *
     * @param token the token to get the authentication from
     * @return the authentication object
     */
    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);

        PrincipalUser principalUser = PrincipalUser.builder()
                .sub((String) claims.get("sub"))
                .storeBranchId((String) claims.get("storeBranchId"))
                .storeId((String) claims.get("storeId"))
                .username((String) claims.get("username"))
                .password(null)
                .authorities(extractAuthorities(claims))
                .accesses(extractAccesses(claims))
                .build();

        return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
    }

    /**
     * getSecret method
     *
     * @param secret the secret to get the key from
     * @return the key object
     */
    private Key getSecret(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    /**
     * extractAuthorities method
     *
     * @param claims the claims to extract the authorities from
     * @return the list of authorities
     */
    private List<GrantedAuthority> extractAuthorities(Claims claims) {
        return ((List<String>) claims.get("roles")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * extractAccesses method
     *
     * @param claims the claims to extract the accesses from
     * @return the list of accesses
     */
    private List<GrantedAuthority> extractAccesses(Claims claims) {
        return ((List<String>) claims.get("accesses")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Value("${jwt.secret}")
    private String secret;
}