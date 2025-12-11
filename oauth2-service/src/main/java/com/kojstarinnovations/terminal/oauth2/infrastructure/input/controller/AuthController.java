package com.kojstarinnovations.terminal.oauth2.infrastructure.input.controller;

import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.data.constants.I18nOAuth2Constants;
import com.kojstarinnovations.terminal.commons.exception.UnauthorizedException;
import com.kojstarinnovations.terminal.oauth2.domain.service.RefreshTokenService;
import com.kojstarinnovations.terminal.oauth2.domain.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout request received");
        return ResponseEntity.ok().build();
    }

    /*@PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody Map<String, String> payload,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

        log.info("Logout request received with payload: {}", payload);

        // 1. Revocar refresh_token si viene
        String refreshToken = payload.get("refresh_token");
        if (refreshToken != null) {
            String sub = tokenService.getSubjectFromRefreshToken(refreshToken);
            refreshTokenService.revokeRefreshToken(sub); // O delete por token si prefieres
        }

        // 2. Limpiar contexto de seguridad
        SecurityContextHolder.clearContext();

        // 3. Invalidar sesión
        request.getSession().invalidate();

        // 4. Eliminar cookies
        deleteCookie("JSESSIONID", request, response);
        deleteCookie("remember-me", request, response);
        deleteCookie("access_token", request, response);

        return ResponseEntity.ok().build();
    }*/

    // Método auxiliar para eliminar cookies
    private void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestParam("refresh_token") String refreshToken) {
        if (!tokenService.validateRefreshToken(refreshToken)) {
            throw new UnauthorizedException(I18nOAuth2Constants.EXCEPTION_REFRESH_TOKEN_NOT_VALID);
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Authentication authentication = tokenService.getAuthenticationFromRefreshToken(refreshToken);

            String sub = tokenService.getSubjectFromRefreshToken(refreshToken);
            String provider = tokenService.getProviderFromRefreshToken(refreshToken);
            //log.info("User ID from refresh token: {}", sub);

            String newAccessToken = tokenService.generateAccessTokenByUserId(authentication, provider, sub);

            //log.info("New access token: {}", newAccessToken);

            return ResponseEntity.ok(Map.of(
                    "access_token", newAccessToken,
                    "refresh_token", refreshToken
            ));
        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
}