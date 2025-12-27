package com.kojstarinnovations.terminal.auths.domain.service;

import com.kojstarinnovations.terminal.auths.domain.opextends.AuthOP;
import com.kojstarinnovations.terminal.auths.domain.opextends.AuthServiceInfoOP;
import com.kojstarinnovations.terminal.auths.domain.ucextends.AuthUC;
import com.kojstarinnovations.terminal.auths.jwt.JwtService;
import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.JwtDTO;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.RefreshTokenData;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.log.BaseLog;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.LoginUser;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.RefreshTokenRequest;
import com.kojstarinnovations.terminal.commons.exception.CriticalSecurityException;
import com.kojstarinnovations.terminal.commons.exception.TwoFactorAuthenticationException;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AuthService - Implementation of the Auth use case interface for authentication purposes.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService implements AuthUC {

    @Override
    public Optional<UserResponse> getByEmail(String email) {
        return outputPort.getByEmail(email);
    }

    @Override
    public JwtDTO login(LoginUser loginUser, String deviceUserAgent, String deviceIp) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        if (principalUser.getStatus() == null || principalUser.getStatus() == Status.BLOCKED) {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_USER_TEMPORARY_BLOCKED);
        }

        // TODO: Validate the user agent and IP address before authenticating
        List<String> suspiciousIps = this.refreshTokenService.getSuspiciousIpsBySub(principalUser.getSub());
        suspiciousIps.stream().filter(ip -> ip.equals(deviceIp)).findFirst().ifPresent(ip -> {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_SUSPICIOUS_IP_DETECTED);
        });

        List<String> suspiciousUserAgents = this.refreshTokenService.getSuspiciousUserAgentsBySub(principalUser.getSub());
        suspiciousUserAgents.stream().filter(userAgent -> userAgent.equals(deviceUserAgent)).findFirst().ifPresent(userAgent -> {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_SUSPICIOUS_DEVICE_DETECTED);
        });

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Instant instant = Instant.now();

        String accessToken = jwtService.generateAccessToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication, instant);
        String tokenId = jwtService.getClaimFromToken(refreshToken, "token_id");
        String sub = jwtService.getClaimFromToken(refreshToken, "sub");
        Instant now = Instant.now();

        this.refreshTokenService.storeRefreshToken(RefreshTokenData.builder()
                        .tokenId(tokenId)
                        .sub(sub)
                        .refreshToken(refreshToken)
                        .userAgent(deviceUserAgent)
                        .build(),
                deviceIp, now, now.plus(30, ChronoUnit.DAYS)
        );

        authServiceInfoOP.save(
                BaseLog.builder()
                        .timestamp(LocalDateTime.now())
                        .userId(sub)
                        .eventType("Login")
                        .details(Map.of(
                                "Service", "AuthService",
                                "Method", "login"
                        ))
                        .build()
        );

        return JwtDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtDTO refreshTokenWithout2fA(RefreshTokenRequest request, String deviceUserAgent, String deviceIp) {
        //TODO: Validate token before refreshing if expired, correctly signed, etc.
        if (jwtService.refreshTokenNotValid(request.refreshToken())) {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_REFRESH_TOKEN_INVALID_OR_EXPIRED);
        }

        String sub = jwtService.getClaimFromToken(request.refreshToken(), "sub");

        // Check if the IP or user agent is blacklisted for the user
        List<String> suspiciousIps = this.refreshTokenService.getSuspiciousIpsBySub(sub);
        suspiciousIps.stream().filter(ip -> ip.equals(deviceIp)).findFirst().ifPresent(ip -> {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_SUSPICIOUS_IP_DETECTED);
        });

        List<String> suspiciousUserAgents = this.refreshTokenService.getSuspiciousUserAgentsBySub(sub);
        suspiciousUserAgents.stream().filter(userAgent -> userAgent.equals(deviceUserAgent)).findFirst().ifPresent(userAgent -> {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_SUSPICIOUS_DEVICE_DETECTED);
        });

        PrincipalUser principalUser = (PrincipalUser) userDetailsServiceImpl.loadUserBySub(sub);

        if (principalUser.getStatus() == null || principalUser.getStatus() == Status.BLOCKED) {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_USER_TEMPORARY_BLOCKED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());

        String newAccessToken = jwtService.generateAccessToken(authentication);

        authServiceInfoOP.save(
                BaseLog.builder()
                        .timestamp(LocalDateTime.now())
                        .userId(sub)
                        .eventType("Refresh Token")
                        .details(Map.of(
                                "Service", "AuthService",
                                "Method", "refreshTokenWithout2fA"
                        ))
                        .build()
        );

        return JwtDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.refreshToken())
                .build();
    }

    @Override
    public JwtDTO refreshToken(RefreshTokenRequest request, String deviceUserAgent, String deviceIp) {
        //TODO: Validate token before refreshing if expired, correctly signed, etc.
        if (jwtService.refreshTokenNotValid(request.refreshToken())) {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_REFRESH_TOKEN_INVALID_OR_EXPIRED);
        }

        String sub = jwtService.getClaimFromToken(request.refreshToken(), "sub");
        String tokenId = this.jwtService.getClaimFromToken(request.refreshToken(), "token_id");
        RefreshTokenData tokenData = this.refreshTokenService.getRefreshToken(tokenId);

        // Check if the IP or user agent is blacklisted for the user
        List<String> suspiciousIps = this.refreshTokenService.getSuspiciousIpsBySub(sub);
        suspiciousIps.stream().filter(ip -> ip.equals(deviceIp)).findFirst().ifPresent(ip -> {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_SUSPICIOUS_IP_DETECTED);
        });

        List<String> suspiciousUserAgents = this.refreshTokenService.getSuspiciousUserAgentsBySub(sub);
        suspiciousUserAgents.stream().filter(userAgent -> userAgent.equals(deviceUserAgent)).findFirst().ifPresent(userAgent -> {
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_SUSPICIOUS_DEVICE_DETECTED);
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
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_USER_TEMPORARY_BLOCKED);
        }

        String savedUserAgent = tokenData.getUserAgent();

        if (!savedUserAgent.equals(deviceUserAgent) && !registeredIp.get() && principalUser.getHasAnyTwoFactorActive()) { // If the user agent and ip are not registered and the user has two-factor authentication enabled, throw a TwoFactorAuthenticationException
            // Send an email, to the user with the code
            //String emailCode = UUIDHelper.generateUUID("", 20);
            throw new TwoFactorAuthenticationException(I18nAuthConstants.EXCEPTION_TWO_FACTOR_FOR_NEW_DEVICE_AND_IP_DETECTED);
        } else if (!savedUserAgent.equals(deviceUserAgent) && !registeredIp.get()) { // If the user agent and ip are not registered and the user does not have two-factor authentication enabled throw an UNAUTHORIZED
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_NEW_DEVICE_AND_IP_DETECTED);
        }

        if (!savedUserAgent.equals(deviceUserAgent) && principalUser.getHasAnyTwoFactorActive()) { // If the user agent no match and the user has two-factor authentication enabled, throw a TwoFactorAuthenticationException
            throw new TwoFactorAuthenticationException(I18nAuthConstants.EXCEPTION_TWO_FACTOR_FOR_NEW_DEVICE_DETECTED);
        } else if (!savedUserAgent.equals(deviceUserAgent)) { // If the user agent is not registered and the user does not have two-factor authentication enabled throw a UNAUTHORIZED
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_NEW_DEVICE_DETECTED);
        }

        if (!registeredIp.get() && principalUser.getHasAnyTwoFactorActive()) { // If the ip is not registered and the user has two-factor authentication enabled, throw a TwoFactorAuthenticationException
            throw new TwoFactorAuthenticationException(I18nAuthConstants.EXCEPTION_TWO_FACTOR_FOR_NEW_IP_DETECTED);
        } else if (!registeredIp.get()) { // If the ip is not registered and the user does not have two-factor authentication enabled throw an UNAUTHORIZED
            throw new CriticalSecurityException(I18nAuthConstants.EXCEPTION_NEW_DEVICE_DETECTED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());

        String newAccessToken = jwtService.generateAccessToken(authentication);

        authServiceInfoOP.save(
                BaseLog.builder()
                        .timestamp(LocalDateTime.now())
                        .userId(sub)
                        .eventType("Refresh Token")
                        .details(Map.of(
                                "Service", "AuthService",
                                "Method", "refreshToken"
                        ))
                        .build()
        );

        return JwtDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.refreshToken())
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        String tokenId = jwtService.getClaimFromToken(refreshToken, "token_id");
        String sub = jwtService.getClaimFromToken(refreshToken, "sub");
        refreshTokenService.revokeRefreshToken(tokenId);

        authServiceInfoOP.save(
                BaseLog.builder()
                        .timestamp(LocalDateTime.now())
                        .userId(sub)
                        .eventType("Session closed")
                        .details(Map.of(
                                "Service", "AuthService",
                                "Method", "logout"
                        ))
                        .build()
        );
    }

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthOP outputPort;

    private final AuthServiceInfoOP authServiceInfoOP;
}