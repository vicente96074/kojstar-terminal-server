package com.kojstarinnovations.terminal.us.jwt;

import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements JwtService {

    // Inyectamos el Decoder que configuraremos con la llave pública
    private final JwtDecoder jwtDecoder;

    @Override
    public boolean validateToken(String token) {
        try {
            // El decoder verifica firma (RS256) y expiración automáticamente
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException ex) {
            log.error("Token JWT inválido: {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        Jwt jwt = jwtDecoder.decode(token);

        // Extraemos los claims del objeto Jwt (mapeo directo de lo que genera tu Auth-Service)
        PrincipalUser principalUser = PrincipalUser.builder()
                .sub(jwt.getClaimAsString("sub"))
                .storeId(jwt.getClaimAsString("storeId"))
                .storeBranchId(jwt.getClaimAsString("storeBranchId"))
                .username(jwt.getClaimAsString("username"))
                .authorities(extractAuthorities(jwt))
                .accesses(extractAccesses(jwt))
                .build();

        return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
    }

    private List<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");
        return roles != null ? roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    private List<GrantedAuthority> extractAccesses(Jwt jwt) {
        List<String> accesses = jwt.getClaimAsStringList("accesses");
        return accesses != null ? accesses.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}