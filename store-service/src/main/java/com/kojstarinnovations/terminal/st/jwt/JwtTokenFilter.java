package com.kojstarinnovations.terminal.st.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.enums.AuthenticationMethod;
import com.kojstarinnovations.terminal.commons.data.enums.ExceptionType;
import com.kojstarinnovations.terminal.commons.data.enums.Methods;
import com.kojstarinnovations.terminal.commons.exception.UnauthorizedException;
import com.kojstarinnovations.terminal.commons.exception.response.ExceptionResponse;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.config.TokenInspector;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {

        try {
            String accessToken = jwtTokenProvider.resolveToken(request);

            if (accessToken == null) {
                throw new UnauthorizedException(I18nAuthConstants.EXCEPTION_UNAUTHORIZED);
            }

            // log.info("Access token: {}", accessToken);

            //log.info("Resolving token: {}", token);

            Map<String, Object> claims = TokenInspector.extractClaimsWithoutValidation(accessToken);

            String authMethod = (String) claims.get(Methods.AUTHENTICATION_METHOD.name().toLowerCase());

            // log.info("Auth method: {}", authMethod);

            boolean isOauth2 = authMethod.equals(AuthenticationMethod.OAuth2.name().toLowerCase());

            //log.info("isOauth2: {}", isOauth2);

            if (isOauth2 && oAuth2JwtService.validateToken(accessToken)) {
                log.info("OAuth2 JWT is valid");
                Authentication auth = oAuth2JwtService.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            if (!isOauth2 && jwtTokenProvider.validateToken(accessToken)) {
                log.info("JWT is valid");
                Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // May not protected AUTH_WHITELIST
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

    private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

//        ExceptionResponse payload = ExceptionResponse
//                .builder()
//                .date(LocalDateTime.now())
//                .type(ExceptionType.CRITICAL_SECURITY)
//                .details(message)
//                .build();

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("date", LocalDateTime.now().toString());
        errorDetails.put("type", ExceptionType.CRITICAL_SECURITY);
        errorDetails.put("details", message);

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
    }

    private static final String[] AUTH_WHITELIST = {
            "/auth/**",
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
            "/actuator/**",
            "/store-service/store/create-by-system"
    };

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2JwtService oAuth2JwtService;
}