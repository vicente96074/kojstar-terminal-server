package com.kojstarinnovations.terminal.storage.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtEntryPoint - Entry point for JWT authentication
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commence method to handle the authentication exception
     *
     * @param request       the request object to be handled
     * @param response      the response object to be handled
     * @param authException the authentication exception to be handled
     * @throws IOException the IO exception to be handled
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}