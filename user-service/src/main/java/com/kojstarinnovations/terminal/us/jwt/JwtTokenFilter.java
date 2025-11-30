package com.kojstarinnovations.terminal.us.jwt;

import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.Methods;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.config.TokenInspector;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * JwtTokenFilter - Filter to handle the token and set the authentication in the security context,
 * this filter used each time a request is made to the server
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = jwtTokenProvider.resolveToken(request);

        log.info("Access token: {}", accessToken);

        //log.info("Resolving token: {}", token);

        Map<String, Object> claims = TokenInspector.extractClaimsWithoutValidation(accessToken);

        String authMethod = (String) claims.get(Methods.AUTHENTICATION_METHOD.name().toLowerCase());

        log.info("Auth method: {}", authMethod);

        boolean isOauth2 = authMethod.equals(AuthenticationMethod.OAuth2.name().toLowerCase());

        //log.info("isOauth2: {}", isOauth2);

        if (isOauth2 && accessToken != null && oAuth2JwtService.validateToken(accessToken)) {
            log.info("OAuth2 JWT is valid");
            Authentication auth = oAuth2JwtService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        if (!isOauth2 && accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            log.info("JWT is valid");
            Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    // May not protected AUTH_WHITELIST
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        for (String authPath : AUTH_WHITELIST) {
            if (path.startsWith(authPath)) {
                return true;
            }
        }
        return false;
    }


    private static final String[] AUTH_WHITELIST = {
            "/auth/**",
            "/email-password/**",
            "/configuration/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v2/api-docs/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/favicon.ico",
            "/user-service/two-factor/add-ip-to-session",
            "/user-service/two-factor/update-user-agent-to-session",
            "/actuator/**",
    };

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2JwtService oAuth2JwtService;
}