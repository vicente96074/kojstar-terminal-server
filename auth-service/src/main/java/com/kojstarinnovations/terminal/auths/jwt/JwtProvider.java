package com.kojstarinnovations.terminal.auths.jwt;

import com.kojstarinnovations.terminal.auths.domain.service.RefreshTokenService;
import com.kojstarinnovations.terminal.commons.data.constants.SystemConstants;
import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.Methods;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * JwtProvider - This class is used to generate, validate and get data from JWT tokens
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements JwtService {

    @Override
    public String generateRefreshToken(Authentication authentication, Instant now) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(hostIssuer)
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS)) // Refresh token más largo
                .subject(principalUser.getSub())
                .claim("token_id", UUIDHelper.generateUUID("rft", 15))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String generateAccessToken(Authentication authentication) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(hostIssuer)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.MINUTES)) // Expire in 15 minutes
                .subject(principalUser.getSub())
                .claim("sub", principalUser.getSub())
                .claim("roles", principalUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .claim("accesses", principalUser.getAccesses().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .claim("storeId", principalUser.getStoreId())
                .claim("storeBranchId", principalUser.getStoreBranchId() != null ? principalUser.getStoreBranchId() : "")
                .claim("provider", SystemConstants.SYSTEM_NICK)
                .claim(String.valueOf(Methods.AUTHENTICATION_METHOD).toLowerCase(), AuthenticationMethod.CUSTOM.name().toLowerCase())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Get claim from token
     *
     * @param token String token
     * @param claim String claim
     * @return String claim
     */
    @Override
    public String getClaimFromToken(String token, String claim) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaimAsString(claim);
    }

    @Override
    public String generateInternalToken() {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(hostIssuer)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(3, ChronoUnit.MINUTES)) // Expire in 3 minutes
                .subject("internal")
                .claim("settingId", "internal")
                .claim("roles", List.of("INTERNAL"))
                .claim("accesses", List.of("INTERNAL"))
                .claim(String.valueOf(Methods.AUTHENTICATION_METHOD).toLowerCase(), AuthenticationMethod.INTERNAL.name().toLowerCase())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    @SneakyThrows
    public boolean refreshTokenNotValid(String refreshToken) {
        try {

            String tokenId = getClaimFromToken(refreshToken, "token_id");
            if (!this.refreshTokenService.isRefreshTokenValid(tokenId, refreshToken)) { // Verificar en Redis
                return true;
            }

            Jwt jwt = jwtDecoder.decode(refreshToken);

            Instant expiration = jwt.getExpiresAt();
            return expiration == null || expiration.isBefore(Instant.now());
        } catch (JwtException e) {
            log.error("Invalid refresh token", e);
            return true;
        }
    }

    @Value("${server.host-issuer}")
    private String hostIssuer;

    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;
    private final RefreshTokenService refreshTokenService;
}