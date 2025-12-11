package com.kojstarinnovations.terminal.oauth2.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.constants.I18nOAuth2Constants;
import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GithubUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GoogleUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.exception.UnauthorizedException;
import com.kojstarinnovations.terminal.commons.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    public String generateAccessToken(Authentication authentication, String email) {
        return generateToken(authentication, 1, ChronoUnit.MINUTES, "read write", "access_token", email);
    }

    public String generateRefreshToken(Authentication authentication, String email) {
        return generateToken(authentication, 30, ChronoUnit.DAYS, "refresh", "refresh_token", email);
    }

    private String generateToken(Authentication authentication, long amount,
                                 ChronoUnit unit, String scope, String tokenType, String email) {
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(hostIssuer)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(amount, unit))
                .subject(authentication.getName())
                .claim("scope", scope);

        applyCustomClaims(claimsBuilder, authentication, tokenType, email);

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();
    }

    public String generateAccessTokenByUserId(Authentication authentication, String provider, String userId) {

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(hostIssuer)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", "read write");

        applyCustomClaimsByUserId(claimsBuilder, authentication, "access_token", provider, userId);

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();
    }

    private void applyCustomClaims(JwtClaimsSet.Builder claimsBuilder,
                                   Authentication authentication, String tokenType, String receivedEmail) {
        String provider = getProvider(authentication);

        if ("access_token".equals(tokenType)) {
            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            String userId = null;
            String storeId = null;
            List<AccessName> accessNames = List.of();

            if ("google".equals(provider)) {
                var googleUser = googleUserService.findByEmail(receivedEmail);
                log.info("Google user: {}", googleUser);
                userId = googleUser.getId();
                storeId = googleUser.getStoreId();

                accessNames = googleUser.getAccessResponses().stream()
                        .map(AccessResponse::getAccessName)
                        .toList();

                roles.addAll(googleUser.getRolResponses().stream()
                        .map(RolResponse::getRolName)
                        .map(RolName::name)
                        .toList());

            } else if ("github".equals(provider)) {
                var githubUser = githubUserService.findByEmail(receivedEmail);
                userId = githubUser.getId();
                storeId = githubUser.getStoreId();

                accessNames = githubUser.getAccessResponses().stream()
                        .map(AccessResponse::getAccessName)
                        .toList();

                roles.addAll(githubUser.getRolResponses().stream()
                        .map(RolResponse::getRolName)
                        .map(RolName::name)
                        .toList());
            } else if ("facebook".equals(provider)) {
                throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
            }

            claimsBuilder
                    .claim("sub", userId != null ? userId : authentication.getName())
                    .claim("roles", roles)
                    .claim("storeId", storeId != null ? storeId : "STRDefault")
                    .claim("username", authentication.getName())
                    .claim("provider", provider)
                    .claim("accesses", accessNames)
                    .claim("authentication_method", AuthenticationMethod.OAuth2.name().toLowerCase());

        } else if ("refresh_token".equals(tokenType)) {
            String userId = null;

            if (provider.equals("google")) {
                var googleUser = googleUserService.findByEmail(receivedEmail);
                userId = googleUser.getId();
            } else if (provider.equals("github")) {
                var githubUser = githubUserService.findByEmail(receivedEmail);
                userId = githubUser.getId();
            }

            claimsBuilder
                    .claim("token_id", UUIDHelper.generateUUID("rft", 15))
                    .claim("sub", userId != null ? userId : authentication.getName())
                    .claim("provider", provider)
            ;
        }
    }

    private void applyCustomClaimsByUserId(JwtClaimsSet.Builder claimsBuilder,
                                           Authentication authentication, String tokenType, String provider, String userId) {

        if ("access_token".equals(tokenType)) {
            Set<String> roles = new HashSet<>(Set.of());
            String storeId = null;
            List<AccessName> accessNames = List.of();

            if ("google".equals(provider)) {
                var googleUser = googleUserService.findById(userId);
                storeId = googleUser.getStoreId();

                accessNames = googleUser.getAccessResponses().stream()
                        .map(AccessResponse::getAccessName)
                        .toList();

                roles.addAll(googleUser.getRolResponses().stream()
                        .map(RolResponse::getRolName)
                        .map(RolName::name)
                        .toList());

            } else if ("github".equals(provider)) {
                var githubUser = githubUserService.findById(userId);
                storeId = githubUser.getStoreId();
                accessNames = githubUser.getAccessResponses().stream()
                        .map(AccessResponse::getAccessName)
                        .toList();

                roles.addAll(githubUser.getRolResponses().stream()
                        .map(RolResponse::getRolName)
                        .map(RolName::name)
                        .toList());
            } else if ("facebook".equals(provider)) {
                throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
            }

            claimsBuilder
                    .claim("sub", userId != null ? userId : authentication.getName())
                    .claim("roles", roles)
                    .claim("storeId", storeId != null ? storeId : "STRDefault")
                    .claim("username", authentication.getName())
                    .claim("provider", provider)
                    .claim("accesses", accessNames)
                    .claim("authentication_method", AuthenticationMethod.OAuth2.name().toLowerCase());
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {

            String tokenId = getClaimFromToken(refreshToken, "token_id");
            if (!this.refreshTokenService.isRefreshTokenValid(tokenId, refreshToken)) { // Verificar en Redis
                return false;
            }

            Jwt jwt = jwtDecoder.decode(refreshToken);

            // Verificar el scope
            String scope = jwt.getClaim("scope");
            if (!"refresh".equals(scope)) {
                log.warn("Token does not have refresh scope");
                return false;
            }

            // Verificar fecha de expiración
            Instant expiration = jwt.getExpiresAt();
            if (expiration == null || expiration.isBefore(Instant.now())) {
                log.warn("Refresh token has expired");
                return false;
            }

            return true;
        } catch (JwtException e) {
            log.error("Invalid refresh token", e);
            return false;
        }
    }

    public String getClaimFromToken(String token, String claim) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaim(claim);
    }

    // Método para obtener autenticación desde el refresh token
    public Authentication getAuthenticationFromRefreshToken(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);

        String subject = jwt.getSubject(); // El "sub" del token
        String issuer = jwt.getIssuer().toString(); // El "iss" del token
        String provider = jwt.getClaim("provider"); // El "provider" del token

        if (!hostIssuer.equals(issuer)) {
            throw new UnauthorizedException(I18nOAuth2Constants.EXCEPTION_INVALID_TOKEN_ISSUER);
        }

        UserDetails userDetails = loadUserDetails(subject, provider);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private UserDetails loadUserDetails(String userId, String provider) {
        // Primero determinamos qué servicio usar según el provider
        if ("google".equalsIgnoreCase(provider)) {
            GoogleUserResponse googleUser = googleUserService.findById(userId);
            if (googleUser != null) {
                return createUserDetailsFromResponse(googleUser);
            }
        } else if ("github".equalsIgnoreCase(provider)) {
            GithubUserResponse githubUser = githubUserService.findById(userId);
            if (githubUser != null) {
                return createUserDetailsFromResponse(githubUser);
            }
        }

        throw new UserNotFoundException(I18nOAuth2Constants.EXCEPTION_USER_NOT_FOUND_BY_PROVIDER);
    }

    // Método auxiliar para crear UserDetails desde los DTOs
    private UserDetails createUserDetailsFromResponse(Object userResponse) {
        if (userResponse instanceof GoogleUserResponse googleUser) {
            return User.withUsername(googleUser.getId())
                    .password("") // No password en OAuth2
                    .authorities(googleUser.getAccessResponses().stream()
                            .map(access -> access.getAccessName().name())
                            .toArray(String[]::new))
                    .build();
        } else if (userResponse instanceof GithubUserResponse githubUser) {
            return User.withUsername(githubUser.getId())
                    .password("") // No password en OAuth2
                    .authorities(githubUser.getAccessResponses().stream()
                            .map(access -> access.getAccessName().name())
                            .toArray(String[]::new))
                    .build();
        }

        throw new IllegalArgumentException(I18nOAuth2Constants.EXCEPTION_PAYLOAD_NOT_SUPPORTED_BY_USER_DETAILS);
    }

    private String getProvider(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            return oauthToken.getAuthorizedClientRegistrationId();
        }
        return "unknown";
    }

    public String getSubjectFromRefreshToken(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        return jwt.getSubject();
    }

    public String getProviderFromRefreshToken(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        return jwt.getClaim("provider");
    }

    private final RefreshTokenService refreshTokenService;

    @Value("${server.host-issuer}")
    private String hostIssuer;

    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;
    private final GoogleUserService googleUserService;
    private final GithubUserService githubUserService;
}