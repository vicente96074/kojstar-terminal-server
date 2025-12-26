package com.kojstarinnovations.terminal.storage.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.ExceptionType;
import com.kojstarinnovations.terminal.commons.data.enums.Methods;
import com.kojstarinnovations.terminal.storage.infrastructure.adapters.config.TokenInspector;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
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

        try {
            log.info("JWT Token Filter");
            String accessToken = jwtTokenProvider.resolveToken(request);
            Map<String, Object> claims = TokenInspector.extractClaimsWithoutValidation(accessToken);
            String authMethod = (String) claims.get(Methods.AUTHENTICATION_METHOD.name().toLowerCase());
            boolean isOauth2 = authMethod.equals(AuthenticationMethod.OAuth2.name().toLowerCase());

            if (isOauth2 && accessToken != null && oAuth2JwtService.validateToken(accessToken)) {
                Authentication auth = oAuth2JwtService.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            if (!isOauth2 && accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handleException(response, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("date", LocalDateTime.now().toString());
        errorDetails.put("type", ExceptionType.CRITICAL_SECURITY);
        errorDetails.put("details", message);

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        for (String authPath : AUTH_WHITELIST) {
            if (pathMatcher.match(authPath, path)) {
                return true;
            }
        }
        return false;
    }

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2JwtService oAuth2JwtService;

    // Whitelist
    private static final String[] AUTH_WHITELIST = {
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
            "/storage-service/get-assets-img",
    };
}