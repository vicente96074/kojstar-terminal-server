package com.kojstarinnovations.terminal.auths.infrastructure.adapters.input.controller;

import com.kojstarinnovations.terminal.auths.domain.service.AuthEmailService;
import com.kojstarinnovations.terminal.auths.domain.service.RefreshTokenService;
import com.kojstarinnovations.terminal.auths.domain.service.UserDetailsServiceImpl;
import com.kojstarinnovations.terminal.auths.jwt.JwtProvider;
import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.JwtDTO;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.RefreshTokenData;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.commons.data.payload.commons.TokenJson;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.RefreshTokenRequest;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.LoginUser;
import com.kojstarinnovations.terminal.commons.data.payload.commons.SimpleMessage;
import com.kojstarinnovations.terminal.commons.data.transport.mail.ForgotPasswordRequest;
import com.kojstarinnovations.terminal.commons.exception.CriticalSecurityException;
import com.kojstarinnovations.terminal.commons.exception.TwoFactorAuthenticationException;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

    /**
     * Endpoint to login on the system
     *
     * @param loginUser       the login user
     * @param deviceUserAgent the device user agent
     * @param deviceIp        the device IP
     * @return the JWT
     */
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUser loginUser,
                                        @RequestHeader("X-Device-User-Agent") String deviceUserAgent,
                                        @RequestHeader("X-Device-IP") String deviceIp
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        if (principalUser.getStatus() == null || principalUser.getStatus() == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        // TODO: Validate the user agent and IP address before authenticating
        List<String> suspiciousIps = this.refreshTokenService.getSuspiciousIpsBySub(principalUser.getSub());
        suspiciousIps.stream().filter(ip -> ip.equals(deviceIp)).findFirst().ifPresent(ip -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_IP_DETECTED);
        });

        List<String> suspiciousUserAgents = this.refreshTokenService.getSuspiciousUserAgentsBySub(principalUser.getSub());
        suspiciousUserAgents.stream().filter(userAgent -> userAgent.equals(deviceUserAgent)).findFirst().ifPresent(userAgent -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_DEVICE_DETECTED);
        });

        Collection<? extends GrantedAuthority> roles = principalUser.getAuthorities();

        if (!roles.contains(new SimpleGrantedAuthority("SUPER_ADMIN"))) {
            String storeId = principalUser.getStoreId(); // Get store id from principal user
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication, createdAt);
        String tokenId = jwtProvider.getClaimFromToken(refreshToken, "token_id");
        String sub = jwtProvider.getClaimFromToken(refreshToken, "sub");
        Instant now = Instant.now();

        this.refreshTokenService.storeRefreshToken(RefreshTokenData.builder()
                        .tokenId(tokenId)
                        .sub(sub)
                        .refreshToken(refreshToken)
                        .userAgent(deviceUserAgent)
                        .build(),
                deviceIp, now, now.plus(30, ChronoUnit.DAYS)
        );

        return new ResponseEntity<>(
                JwtDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<SimpleMessage> logout(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        String tokenId = jwtProvider.getClaimFromToken(refreshToken, "token_id");
        refreshTokenService.revokeRefreshToken(tokenId);

        return new ResponseEntity<>(
                SimpleMessage.builder()
                        .message("Logout successful")
                        .build()
                , HttpStatus.OK);
    }

    /**
     * Endpoint to refresh the token
     *
     * @param request the refresh token request
     * @return the JWT
     */
    @PostMapping(value = "/refresh-token-without-2fa", produces = "application/json")
    public ResponseEntity<JwtDTO> refreshTokenWithout2fA(
            @RequestBody RefreshTokenRequest request,
            @RequestHeader("X-Device-User-Agent") String deviceUserAgent,
            @RequestHeader("X-Device-IP") String deviceIp
    ) {
        //TODO: Validate token before refreshing if expired, correctly signed, etc.
        if (!jwtProvider.validateRefreshToken(request.refreshToken())) {
            throw new CriticalSecurityException(ExceptionConstants.REFRESH_TOKEN_INVALID_OR_EXPIRED);
        }

        String sub = jwtProvider.getClaimFromToken(request.refreshToken(), "sub");

        // Check if the IP or user agent is blacklisted for the user
        List<String> suspiciousIps = this.refreshTokenService.getSuspiciousIpsBySub(sub);
        suspiciousIps.stream().filter(ip -> ip.equals(deviceIp)).findFirst().ifPresent(ip -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_IP_DETECTED);
        });

        List<String> suspiciousUserAgents = this.refreshTokenService.getSuspiciousUserAgentsBySub(sub);
        suspiciousUserAgents.stream().filter(userAgent -> userAgent.equals(deviceUserAgent)).findFirst().ifPresent(userAgent -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_DEVICE_DETECTED);
        });

        PrincipalUser principalUser = (PrincipalUser) userDetailsServiceImpl.loadUserBySub(sub);

        if (principalUser.getStatus() == null || principalUser.getStatus() == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());

        String newAccessToken = jwtProvider.generateAccessToken(authentication);

        log.info("Token refreshed for user: {} at {}", sub, LocalDateTime.now());

        return new ResponseEntity<>(
                JwtDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(request.refreshToken())
                        .build(), HttpStatus.OK);
    }

    /**
     * Endpoint to refresh the token
     *
     * @param request         the refresh token request
     * @param deviceUserAgent the device user agent
     * @param deviceIp        the device IP
     * @return the JWT
     */
    @PostMapping(value = "/refresh-token", produces = "application/json")
    public ResponseEntity<JwtDTO> refreshToken(@RequestBody RefreshTokenRequest request,
                                               @RequestHeader("X-Device-User-Agent") String deviceUserAgent,
                                               @RequestHeader("X-Device-IP") String deviceIp
    ) {
        //TODO: Validate token before refreshing if expired, correctly signed, etc.
        if (!jwtProvider.validateRefreshToken(request.refreshToken())) {
            throw new CriticalSecurityException(ExceptionConstants.REFRESH_TOKEN_INVALID_OR_EXPIRED);
        }

        String sub = jwtProvider.getClaimFromToken(request.refreshToken(), "sub");
        String tokenId = this.jwtProvider.getClaimFromToken(request.refreshToken(), "token_id");
        RefreshTokenData tokenData = this.refreshTokenService.getRefreshToken(tokenId);

        // Check if the IP or user agent is blacklisted for the user
        List<String> suspiciousIps = this.refreshTokenService.getSuspiciousIpsBySub(sub);
        suspiciousIps.stream().filter(ip -> ip.equals(deviceIp)).findFirst().ifPresent(ip -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_IP_DETECTED);
        });

        List<String> suspiciousUserAgents = this.refreshTokenService.getSuspiciousUserAgentsBySub(sub);
        suspiciousUserAgents.stream().filter(userAgent -> userAgent.equals(deviceUserAgent)).findFirst().ifPresent(userAgent -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_DEVICE_DETECTED);
        });

        // May two-factor authentication for ip change
        List<String> tokenIps = this.refreshTokenService.getTokenIpsByTokenId(tokenId);

        // If ips contains the device ip, then the token is valid
        AtomicReference<Boolean> registeredIp = new AtomicReference<>(false);
        tokenIps.forEach(ip -> {
            if (ip.equals(deviceIp)) {
                registeredIp.set(true);
            }
        });

        PrincipalUser principalUser = (PrincipalUser) userDetailsServiceImpl.loadUserBySub(sub);

        if (principalUser.getStatus() == null || principalUser.getStatus() == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        String savedUserAgent = tokenData.getUserAgent();

        if (!savedUserAgent.equals(deviceUserAgent) && !registeredIp.get() && principalUser.getHasAnyTwoFactorActive()) { // If the user agent and ip are not registered and the user has two-factor authentication enabled, throw a TwoFactorAuthenticationException
            // Send an email, to the user with the code
            String emailCode = UUIDHelper.generateUUID("", 20);
            log.info("Email sent to user with code: {}", emailCode);
            throw new TwoFactorAuthenticationException(ExceptionConstants.TWO_FACTOR_FOR_NEW_DEVICE_AND_IP_DETECTED);
        } else if (!savedUserAgent.equals(deviceUserAgent) && !registeredIp.get()) { // If the user agent and ip are not registered and the user does not have two-factor authentication enabled throw an UNAUTHORIZED
            throw new CriticalSecurityException(ExceptionConstants.NEW_DEVICE_AND_IP_DETECTED);
        }

        if (!savedUserAgent.equals(deviceUserAgent) && principalUser.getHasAnyTwoFactorActive()) { // If the user agent no match and the user has two-factor authentication enabled, throw a TwoFactorAuthenticationException
            throw new TwoFactorAuthenticationException(ExceptionConstants.TWO_FACTOR_FOR_NEW_DEVICE_DETECTED);
        } else if (!savedUserAgent.equals(deviceUserAgent)) { // If the user agent is not registered and the user does not have two-factor authentication enabled throw a UNAUTHORIZED
            throw new CriticalSecurityException(ExceptionConstants.NEW_DEVICE_DETECTED);
        }

        if (!registeredIp.get() && principalUser.getHasAnyTwoFactorActive()) { // If the ip is not registered and the user has two-factor authentication enabled, throw a TwoFactorAuthenticationException
            throw new TwoFactorAuthenticationException(ExceptionConstants.TWO_FACTOR_FOR_NEW_IP_DETECTED);
        } else if (!registeredIp.get()) { // If the ip is not registered and the user does not have two-factor authentication enabled throw an UNAUTHORIZED
            throw new CriticalSecurityException(ExceptionConstants.NEW_DEVICE_DETECTED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());

        String newAccessToken = jwtProvider.generateAccessToken(authentication);

        log.info("Token refreshed for user: {} at {}", sub, LocalDateTime.now());

        return new ResponseEntity<>(
                JwtDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(request.refreshToken())
                        .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenJson> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return new ResponseEntity<>(authEmailService.sendEmailForgotPassword(request), HttpStatus.OK);
    }

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RefreshTokenService refreshTokenService;
    private final AuthEmailService authEmailService;
}