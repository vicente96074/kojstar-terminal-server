package com.kojstarinnovations.terminal.auths.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.auths.domain.service.AuthEmailService;
import com.kojstarinnovations.terminal.auths.domain.service.AuthService;
import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.JwtDTO;
import com.kojstarinnovations.terminal.commons.data.payload.commons.TokenJson;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.RefreshTokenRequest;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.LoginUser;
import com.kojstarinnovations.terminal.commons.data.payload.commons.SimpleMessage;
import com.kojstarinnovations.terminal.commons.data.transport.mail.ForgotPasswordRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AuthController - Controller for the authentication endpoints
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@RestController
@RequestMapping("/auth-service/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUser loginUser,
                                        @RequestHeader("X-Device-User-Agent") String deviceUserAgent,
                                        @RequestHeader("X-Device-IP") String deviceIp
    ) {
        return new ResponseEntity<>(authService.login(loginUser, deviceUserAgent, deviceIp), HttpStatus.OK);
    }

    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<SimpleMessage> logout(@RequestBody Map<String, String> payload) {
        authService.logout(payload.get("refreshToken"));

        return new ResponseEntity<>(
                SimpleMessage.builder()
                        .message(I18nAuthConstants.SUCCESS_LOGOUT)
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping(value = "/refresh-token-without-2fa", produces = "application/json")
    public ResponseEntity<JwtDTO> refreshTokenWithout2fA(
            @RequestBody RefreshTokenRequest request,
            @RequestHeader("X-Device-User-Agent") String deviceUserAgent,
            @RequestHeader("X-Device-IP") String deviceIp
    ) {
        return new ResponseEntity<>(authService.refreshTokenWithout2fA(request, deviceUserAgent, deviceIp), HttpStatus.OK);
    }

    @PostMapping(value = "/refresh-token", produces = "application/json")
    public ResponseEntity<JwtDTO> refreshToken(@RequestBody RefreshTokenRequest request,
                                               @RequestHeader("X-Device-User-Agent") String deviceUserAgent,
                                               @RequestHeader("X-Device-IP") String deviceIp
    ) {
        return new ResponseEntity<>(authService.refreshToken(request, deviceUserAgent, deviceIp), HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenJson> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return new ResponseEntity<>(authEmailService.sendEmailForgotPassword(request), HttpStatus.OK);
    }


    private final AuthService authService;
    private final AuthEmailService authEmailService;
}