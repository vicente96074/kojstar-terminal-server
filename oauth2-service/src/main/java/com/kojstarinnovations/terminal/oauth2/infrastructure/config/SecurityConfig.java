package com.kojstarinnovations.terminal.oauth2.infrastructure.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kojstarinnovations.terminal.oauth2.domain.service.InterceptOauth2UserService;
import com.kojstarinnovations.terminal.oauth2.domain.service.RefreshTokenService;
import com.kojstarinnovations.terminal.oauth2.domain.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/auth/**",
                                "/client/**",
                                "/login/oauth2/code/**",
                                "/api/auth/token",
                                "/api/auth/refresh-token",
                                "/api/auth/logout",
                                "/.well-known/openid-configuration",
                                "/oauth2/jwks"
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/client/**",
                                "/oauth2/**",
                                "/login/oauth2/code/**",
                                "/api/auth/token",
                                "/api/auth/logout",
                                "/api/auth/refresh-token",
                                "/.well-known/openid-configuration",
                                "/oauth2/jwks"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(authorizationRequestRepository())
                        )
                        .redirectionEndpoint(redir -> redir
                                .baseUri("/login/oauth2/code/*")
                        )
                        .successHandler((request, response, authentication) -> {

                            String originalState = (String) request.getSession().getAttribute("OAUTH2_ORIGINAL_STATE");
                            String decodedState = URLDecoder.decode(originalState, StandardCharsets.UTF_8);
                            JsonNode stateJson = new ObjectMapper().readTree(decodedState);

                            String frontendUrl = stateJson.path("frontendUrl").asText(null);
                            //log.info("frontendUrl: {}", frontendUrl);

                            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                            String provider = oauthToken.getAuthorizedClientRegistrationId();
                            Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

                            String email;
                            if ("github".equalsIgnoreCase(provider)) {
                                email = getGithubEmail(oauthToken, attributes);
                            } else {
                                email = (String) attributes.get("email"); // Para otros proveedores
                            }

                            interceptOauth2UserService.intercept(stateJson, email, provider, attributes);

                            String accessToken = tokenService.generateAccessToken(authentication, email);
                            String refreshToken = tokenService.generateRefreshToken(authentication, email);
                            String tokenId = tokenService.getClaimFromToken(refreshToken, "token_id");
                            Instant now = Instant.now();
                            refreshTokenService.storeRefreshToken(tokenId, refreshToken, now, now.plus(30, ChronoUnit.DAYS));

                            String redirectUrl = String.format("%s/oauth-callback?access_token=%s&refresh_token=%s&provider=%s", frontendUrl,
                                    URLEncoder.encode(accessToken, StandardCharsets.UTF_8),
                                    URLEncoder.encode(refreshToken, StandardCharsets.UTF_8),
                                    URLEncoder.encode(provider, StandardCharsets.UTF_8)
                            );

                            response.sendRedirect(redirectUrl);
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Limpiar contexto de seguridad
                            SecurityContextHolder.clearContext();

                            // Invalidar sesión
                            request.getSession().invalidate();

                            String refreshToken = request.getParameter("refresh_token");
                            String tokenId = tokenService.getClaimFromToken(refreshToken, "token_id");

                            refreshTokenService.revokeRefreshToken(tokenId);

                            // Respuesta simple
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().flush();
                        })
                        .deleteCookies("JSESSIONID", "remember-me", "access_token", "refresh_token")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            String idp = request.getParameter("idp");
                            String redirectUrl = "/oauth2/authorization/" + (idp != null ? idp : "google");
                            new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
                        })
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

    private String getGithubEmail(OAuth2AuthenticationToken oauthToken, Map<String, Object> attributes) {
        // Primero intenta con el email en los atributos básicos
        String email = (String) attributes.get("email");

        if (email == null) {
            // Si no está en los atributos básicos, hacer llamada a la API de GitHub
            email = fetchPrimaryEmailFromGithubApi(oauthToken);
        }

        return email;
    }

    private String fetchPrimaryEmailFromGithubApi(OAuth2AuthenticationToken oauthToken) {
        try {
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName());

            String response = WebClient.create()
                    .get()
                    .uri("https://api.github.com/user/emails")
                    .header(HttpHeaders.AUTHORIZATION, "token " + client.getAccessToken().getTokenValue())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode emails = new ObjectMapper().readTree(response);
            for (JsonNode emailNode : emails) {
                if (emailNode.get("primary").asBoolean()) {
                    return emailNode.get("email").asText();
                }
            }
        } catch (Exception e) {
            log.error("Error fetching GitHub emails", e);
        }
        return null;
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new AuthorizationRequestRepository<>() {
            private final HttpSessionOAuth2AuthorizationRequestRepository delegate =
                    new HttpSessionOAuth2AuthorizationRequestRepository();

            @Override
            public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
                return delegate.loadAuthorizationRequest(request);
            }

            @Override
            public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
                // Guardar el state original en la sesión
                String originalState = request.getParameter("state");

                if (originalState != null) {
                    request.getSession().setAttribute("OAUTH2_ORIGINAL_STATE", originalState);
                }

                OAuth2AuthorizationRequest modifiedRequest = OAuth2AuthorizationRequest.from(authorizationRequest)
                        .additionalParameters(params -> {
                            params.put("prompt", "consent");
                            params.put("access_type", "offline");
                        })
                        .build();

                delegate.saveAuthorizationRequest(modifiedRequest, request, response);
            }

            @Override
            public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                         HttpServletResponse response) {
                return delegate.removeAuthorizationRequest(request, response);
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(ALLOWED_ORIGINS.split(",")));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/auth/**", configuration);
        source.registerCorsConfiguration("/.well-known/**", configuration);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            String idp = request.getParameter("idp"); // ?idp=google o ?idp=github
            String redirectUrl = "/oauth2/authorization/" + (idp != null ? idp : "google");
            new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
        };
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();
        client.setRequestEntityConverter(new OAuth2AuthorizationCodeGrantRequestEntityConverter());
        return client;
    }

    /**
     * Token Settings for the OAuth2 Authorization Server
     *
     * @return TokenSettings
     */
    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(30))
                .refreshTokenTimeToLive(Duration.ofDays(1))
                .build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer(KOJSTAR_TERMINAL_OAUTH2_HOST_ISSUER).build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Value("${cors.allowed-origins}")
    private String ALLOWED_ORIGINS;
    @Value("${server.host-issuer}")
    private String KOJSTAR_TERMINAL_OAUTH2_HOST_ISSUER;

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final InterceptOauth2UserService interceptOauth2UserService;
}