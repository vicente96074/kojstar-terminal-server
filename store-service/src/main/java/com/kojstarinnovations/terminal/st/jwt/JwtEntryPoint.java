package com.kojstarinnovations.terminal.st.jwt;

import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtEntryPoint - Entry point implementation to handle the authentication entry point to commence an authentication schema
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
     */
    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        //log.error("Unauthorized error: {}", authException.getMessage());
        //authException.printStackTrace();
        throw new UnauthorizedException(I18nAuthConstants.EXCEPTION_UNAUTHORIZED);
        //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}