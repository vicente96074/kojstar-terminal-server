package com.kojstarinnovations.terminal.auths.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * JwtEntryPoint - This class is used to handle the authentication exception
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
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
        Logger.getLogger("JwtEntryPoint").info("Unauthorized error. Message: " + authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
