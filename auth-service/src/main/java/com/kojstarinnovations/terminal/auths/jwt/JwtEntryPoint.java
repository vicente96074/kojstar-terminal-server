package com.kojstarinnovations.terminal.auths.jwt;

import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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
     */
    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        Logger.getLogger("JwtEntryPoint").info("Unauthorized error. Message: " + authException.getMessage());
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        throw new UnauthorizedException(I18nAuthConstants.EXCEPTION_UNAUTHORIZED);
    }

}
