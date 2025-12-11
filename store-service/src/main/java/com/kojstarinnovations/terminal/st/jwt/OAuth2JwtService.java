package com.kojstarinnovations.terminal.st.jwt;

import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OAuth2JwtService - Class to provide the methods to validate a token and get the authentication from it,
 * It implements JwtService to provide the methods,
 * It uses OAuth2 to validate the token
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
public class OAuth2JwtService implements JwtService {

    /**
     * Get jwt decoder from issuer uri
     *
     * @return JwtDecoder
     */
    private JwtDecoder getDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    /**
     * Clean token method
     *
     * @param token the token to be cleaned
     * @return the cleaned token
     */
    private String cleanToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }

    /**
     * validateToken method
     *
     * @param token the token to be validated
     * @return a boolean value to indicate if the token is valid or not
     */
    @Override
    public boolean validateToken(String token) {
        try {
            String cleanedToken = cleanToken(token);
            getDecoder().decode(cleanedToken);
            return true;
        } catch (Exception ex) {
            log.error("Error validating token: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * getUsernameFromToken method
     *
     * @param token the token to get the username from
     * @return the username from the token
     */
    public String getUsernameFromToken(String token) {
        String cleanedToken = cleanToken(token);
        return getDecoder().decode(cleanedToken).getSubject();
    }

    /**
     * getAuthentication method
     *
     * @param token the token to get the authentication from
     * @return the authentication object
     */
    @Override
    public Authentication getAuthentication(String token) {
        try {
            String cleanedToken = cleanToken(token);
            Jwt jwt = getDecoder().decode(cleanedToken);

            // Convertir roles a GrantedAuthority
            List<SimpleGrantedAuthority> authorities = jwt.getClaimAsStringList("roles")
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Convertir accesos a GrantedAuthority si existen
            List<SimpleGrantedAuthority> accesses = jwt.getClaimAsStringList("accesses") != null ?
                    jwt.getClaimAsStringList("accesses")
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()) :
                    List.of();

            PrincipalUser principalUser = PrincipalUser.builder()
                    .sub(jwt.getSubject())
                    .storeBranchId(jwt.getClaimAsString("storeBranchId"))
                    .storeId(jwt.getClaimAsString("storeId"))
                    .password(null)
                    .authorities(authorities)
                    .accesses(accesses)
                    .build();

            //log.info("Principal user: {}", principalUser);

            return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());

        } catch (Exception ex) {
            log.error("Error creating authentication from token: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error creating authentication: " + ex.getMessage(), ex);
        }
    }

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;
}