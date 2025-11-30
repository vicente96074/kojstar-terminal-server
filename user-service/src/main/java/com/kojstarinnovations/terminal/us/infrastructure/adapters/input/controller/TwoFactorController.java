package com.kojstarinnovations.terminal.us.infrastructure.adapters.input.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.constants.SuccessConstants;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.TwoFactorCode;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.commons.data.payload.authentication.PhonePayload;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorStatus;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.authservice.RefreshTokenRequest;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.TwoFactorCodes;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.TwoFactorUserRequest;
import com.kojstarinnovations.terminal.commons.exception.BadOperationException;
import com.kojstarinnovations.terminal.commons.exception.CriticalSecurityException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.commons.exception.PersonalizedException;
import com.kojstarinnovations.terminal.commons.exception.response.PersonalizedDetailsResponse;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import com.kojstarinnovations.terminal.us.domain.service.RefreshTokenService;
import com.kojstarinnovations.terminal.us.domain.service.TwoFactorService;
import com.kojstarinnovations.terminal.us.domain.service.UserDetailsServiceImpl;
import com.kojstarinnovations.terminal.us.domain.service.UserService;
import com.kojstarinnovations.terminal.us.domain.ucextends.ContactUSUC;
import com.kojstarinnovations.terminal.us.domain.ucextends.TwoFactorUserUC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static com.kojstarinnovations.terminal.commons.data.constants.RedisConstants.MAX_ATTEMPTS_BY_TWO_FACTOR;

/**
 * TwoFactorController - Controller for handling two-factor authentication
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@RestController
@RequestMapping("/user-service/two-factor")
@RequiredArgsConstructor
public class TwoFactorController {

    /**
     * Generates a secret for the authenticator app
     *
     * @return ResponseEntity with the OTP Auth URL
     */
    @PostMapping(value = "/generate-secret-by-authenticator-app", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<Map<String, String>> generateSecretByAuthenticatorApp() {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        UserResponse user = userService.getUserById(principal.getSub());

        if (user.getStatus() == null || user.getStatus() == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        String secret = twoFactorUserService.getTwoFactorAppSecretByUserId(principal.getSub());
        String otpAuthUrl;

        if (secret != null && !secret.isEmpty()) {
            otpAuthUrl = twoFactorService.getOtpAuthURL("Kojnexus", user.getEmail(), secret);
            return ResponseEntity.ok(Map.of("otpAuthUrl", otpAuthUrl));
        }

        secret = twoFactorService.generateSecretKey();
        otpAuthUrl = twoFactorService.getOtpAuthURL("Kojnexus", user.getEmail(), secret);
        twoFactorUserService.save(
                TwoFactorUserRequest.builder()
                        .userId(principal.getSub())
                        .twoFactorType(TwoFactorType.AUTHENTICATOR_APP)
                        .twoFactorSecret(secret)
                        .isPrimary(!twoFactorUserService.hasAnyPrimaryByUserId(principal.getSub()))
                        .build());
        return ResponseEntity.ok(Map.of("otpAuthUrl", otpAuthUrl));
    }

    @GetMapping(value = "/get-sms-number-by-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<List<PhonePayload>> getCurrentSmsNumberByAuth() {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        TwoFactorUserResponse twoFactorUserResponse = twoFactorUserService.get2FASmsBySub(principal.getSub());

        if (twoFactorUserResponse != null) {
            return ResponseEntity.ok(List.of(
                    PhonePayload.builder()
                            .id(twoFactorUserResponse.getId())
                            .userId(twoFactorUserResponse.getUserId())
                            .phoneNumber(twoFactorUserResponse.getTwoFactorPhone())
                            .countryCode(twoFactorUserResponse.getTwoFactorCountryCode().getCode())
                            .build()
            ));
        }

        List<ContactUSResponse> contactUSResponseList = contactService.getAllActivePhonesByAuth();
        if (contactUSResponseList.isEmpty()) {
            throw new NotFoundException(ExceptionConstants.PHONE_NUMBER_NOT_REGISTERED);
        }

        return ResponseEntity.ok(contactUSResponseList.stream()
                .map(contact -> PhonePayload.builder()
                        .id(contact.getId())
                        .userId(contact.getUserId())
                        .phoneNumber(contact.getPhoneNumber())
                        .countryCode(contact.getCountryCode())
                        .build())
                .toList());
    }

    /**
     * Generates a secret for the SMS authenticator
     *
     * @return ResponseEntity with success message
     */
    @PostMapping(value = "/generate-secret-by-authenticator-sms", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<?> generateSecretByAuthenticatorSms(
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("countryCode") String countryCode
    ) {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        TwoFactorCode currentCode = this.refreshTokenService.getCodeSentToSmsBySub(principal.getSub());

        if (currentCode != null) {
            throw new PersonalizedException(
                    PersonalizedDetailsResponse.builder()
                            .message(ExceptionConstants.TWO_FACTOR_SMS_CODE_ALREADY_SENT)
                            .duration(Duration.between(Instant.now(), currentCode.getExpiration()).getSeconds())
                            .build().toString()
            );
        }

        CountryCodeISO countryCodeISO = CountryCodeISO.fromCode(countryCode)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.COUNTRY_CODE_NOT_FOUND));
        String fullPhoneNumber = countryCode + phoneNumber;

        boolean anyPhoneRegistered = twoFactorUserService.existPhoneBySub(principal.getSub());
        boolean matchRegisteredPhone = twoFactorUserService.matchPhoneBySubAndCountryCode(principal.getSub(), phoneNumber, countryCodeISO);

        if (!anyPhoneRegistered && !contactService.existPhoneBySubAndCountryCode(phoneNumber, principal.getSub(), countryCode)) {
            throw new BadOperationException(ExceptionConstants.PHONE_NUMBER_NOT_REGISTERED);
        }

        if (anyPhoneRegistered && !matchRegisteredPhone) {
            throw new BadOperationException(ExceptionConstants.PHONE_NUMBER_MISMATCH);
        }

        Integer code = UUIDHelper.generateRandomNumber(6);
        Instant issuedAt = Instant.now();

        // Store code in the database and send it via SMS
        this.refreshTokenService.storeCodeSentToSmsBySub(
                TwoFactorCode.builder()
                        .code(code)
                        .type(TwoFactorType.AUTHENTICATOR_SMS)
                        .phoneNumber(fullPhoneNumber)
                        .sub(principal.getSub())
                        .issuedAt(issuedAt)
                        .expiration(issuedAt.plusSeconds(60 * 10)) // 10 minutes expiration
                        .used(false)
                        .build(),
                principal.getSub());

        // Verify if the phone number is registered on two-factor service
        if (!anyPhoneRegistered) { // No need to persist the two-factor sms again
            // save the authenticator sms
            this.twoFactorUserService.save(
                    TwoFactorUserRequest.builder()
                            .userId(principal.getSub())
                            .twoFactorType(TwoFactorType.AUTHENTICATOR_SMS)
                            .twoFactorSecret(null)
                            .twoFactorPhone(phoneNumber)
                            .twoFactorCountryCode(countryCodeISO)
                            .isPrimary(!twoFactorUserService.hasAnyPrimaryByUserId(principal.getSub()))
                            .build());
        }

        // TODO: Send the code via SMS

        return ResponseEntity.ok(Map.of("message", SuccessConstants.TWO_FACTOR_SMS_CODE_SENT));
    }

    /**
     * Generates a secret for the email authenticator
     *
     * @return ResponseEntity with success message
     */
    @PostMapping(value = "/generate-secret-by-authenticator-email", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<?> generateSecretByAuthenticatorEmail(@RequestParam("email") String email) {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        boolean anyEmailRegistered = twoFactorUserService.existEmailBySub(principal.getSub());
        boolean matchRegisteredEmail = twoFactorUserService.matchEmailBySub(principal.getSub(), email);

        if (!anyEmailRegistered && !contactService.existEmailBySub(email, principal.getSub())) {
            throw new BadOperationException(ExceptionConstants.EMAIL_NOT_REGISTERED);
        }

        if (anyEmailRegistered && !matchRegisteredEmail) {
            throw new BadOperationException(ExceptionConstants.EMAIL_MISMATCH);
        }

        Integer code = UUIDHelper.generateRandomNumber(6);
        Instant issuedAt = Instant.now();

        // Store code in the database and send it via email
        this.refreshTokenService.storeCodeSentToEmailBySub(
                TwoFactorCode.builder()
                        .code(code)
                        .type(TwoFactorType.AUTHENTICATOR_EMAIL)
                        .email(email)
                        .sub(principal.getSub())
                        .issuedAt(issuedAt)
                        .expiration(issuedAt.plusSeconds(60 * 10)) // 10 minutes expiration
                        .used(false)
                        .build(),
                principal.getSub());

        if (!anyEmailRegistered) {
            this.twoFactorUserService.save(
                    TwoFactorUserRequest.builder()
                            .userId(principal.getSub())
                            .twoFactorType(TwoFactorType.AUTHENTICATOR_EMAIL)
                            .twoFactorSecret(null)
                            .twoFactorEmail(email)
                            .isPrimary(!twoFactorUserService.hasAnyPrimaryByUserId(principal.getSub()))
                            .build());
        }

        // TODO: Send the code via email

        return ResponseEntity.ok(Map.of("message", SuccessConstants.TWO_FACTOR_EMAIL_CODE_SENT));
    }

    /**
     * Enables and activates the two-factor authentication for the user
     *
     * @param appCode generated code from the authenticator app
     * @return ResponseEntity with success message
     */
    @PostMapping(value = "/enable-and-active-two-factor-app", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<?> enableAndActivateTwoFactorApp(@RequestParam("appCode") Integer appCode) {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        String secret = twoFactorUserService.getTwoFactorAppSecretByUserId(principal.getSub());

        if (secret == null || secret.isEmpty()) {
            throw new BadOperationException(ExceptionConstants.TWO_FACTOR_APP_NOT_ENABLED);
        }

        if (twoFactorService.isCodeInvalid(secret, appCode)) {
            throw new BadOperationException(ExceptionConstants.INVALID_TWO_FACTOR_CODE);
        }

        twoFactorUserService.enableAndActiveTwoFactorAppByUserId(principal.getSub());

        return ResponseEntity.ok(Map.of("message", SuccessConstants.TWO_FACTOR_ENABLED));
    }

    /**
     * Enables and activates the two-factor authentication for the user
     *
     * @param smsCode code sent to the user by SMS
     * @return ResponseEntity with success message
     */
    @PostMapping(value = "/enable-and-active-2fa-sms", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<?> enableAndActivateTwoFactorSms(@RequestParam("smsCode") Integer smsCode) {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        TwoFactorCode secret = this.refreshTokenService.getCodeSentToSmsBySub(principal.getSub());

        if (secret == null) {
            throw new BadOperationException(ExceptionConstants.TWO_FACTOR_SMS_NOT_ENABLED);
        }

        Instant now = Instant.now();
        if (secret.getExpiration().isBefore(now)) { // check if the code is expired
            throw new BadOperationException(ExceptionConstants.SMS_CODE_EXPIRED);
        }

        if (smsCode == null || !smsCode.equals(secret.getCode())) {
            throw new BadOperationException(ExceptionConstants.INVALID_TWO_FACTOR_CODE);
        }

        // Remove the code from the database
        refreshTokenService.removeCodeSentToSmsBySub(principal.getSub());

        twoFactorUserService.enableAndActiveTwoFactorSmsByUserId(principal.getSub());

        return ResponseEntity.ok(Map.of("message", SuccessConstants.TWO_FACTOR_ENABLED));
    }

    @PostMapping(value = "/enable-and-active-two-factor-email", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<?> enableAndActivateTwoFactorEmail(@RequestParam("emailCode") Integer emailCode) {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        TwoFactorCode secret = this.refreshTokenService.getCodeSentToSmsBySub(principal.getSub());

        if (secret == null) {
            throw new BadOperationException(ExceptionConstants.TWO_FACTOR_SMS_NOT_ENABLED);
        }

        Instant now = Instant.now();
        if (secret.getExpiration().isBefore(now)) { // check if the code is expired
            throw new BadOperationException(ExceptionConstants.EMAIL_CODE_EXPIRED);
        }

        if (emailCode == null || !emailCode.equals(secret.getCode())) {
            throw new BadOperationException(ExceptionConstants.INVALID_TWO_FACTOR_CODE);
        }

        // Mark the code as used
        secret.setUsed(true);
        this.refreshTokenService.storeCodeSentToEmailBySub(secret, principal.getSub()); // update the code in the database

        twoFactorUserService.enableAndActiveTwoFactorEmailByUserId(principal.getSub());

        return ResponseEntity.ok(Map.of("message", SuccessConstants.TWO_FACTOR_ENABLED));
    }

    @PostMapping(value = "/deactivate-two-factor-app", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<?> deactivate2FA(@RequestParam("code") Integer code) {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        String secret = twoFactorUserService.getTwoFactorAppSecretByUserId(principal.getSub());

        if (secret == null || secret.isEmpty()) {
            throw new BadOperationException(ExceptionConstants.TWO_FACTOR_APP_NOT_ENABLED);
        }

        if (twoFactorService.isCodeInvalid(secret, code)) {
            refreshTokenService.addOrUpdateAttemptsBySub(principal.getSub());
            throw new BadOperationException(ExceptionConstants.INVALID_TWO_FACTOR_CODE);
        }

        twoFactorUserService.deactivateTwoFactorAppByUserId(principal.getSub());
        return ResponseEntity.ok(Map.of("message", SuccessConstants.TWO_FACTOR_DEACTIVATED));
    }


    @PostMapping(value = "/add-ip-to-session", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewIpToSession(
            @RequestHeader("X-Device-IP") String deviceIp,
            @RequestParam("sub") String sub,
            @RequestBody Map<String, Object> request
    ) {
        RefreshTokenRequest tokenRequest = objectMapper.convertValue(request.get("refreshTokenRequest"), RefreshTokenRequest.class);
        TwoFactorCodes codes = objectMapper.convertValue(request.get("twoFactorCode"), TwoFactorCodes.class);

        List<String> suspiciousIps = refreshTokenService.getSuspiciousIpsBySub(sub);
        suspiciousIps.stream().filter(ip -> ip.equals(deviceIp)).findFirst().ifPresent(ip -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_IP_DETECTED);
        });

        Status status = userService.getStatusById(sub);

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        String tokenId = refreshTokenService.getClaimFromToken(tokenRequest.refreshToken(), "token_id");
        Integer tries = this.refreshTokenService.getTriesBySub(sub);

        if (tries >= MAX_ATTEMPTS_BY_TWO_FACTOR) {
            throw new CriticalSecurityException(ExceptionConstants.SEVERAL_FAILED_TWO_FACTOR_ATTEMPTS);
        }

        String secret = twoFactorUserService.getTwoFactorAppSecretByUserId(sub);

        if (secret == null || secret.isEmpty()) {
            throw new BadOperationException(ExceptionConstants.TWO_FACTOR_APP_NOT_ENABLED);
        }

        if (twoFactorService.isCodeInvalid(secret, codes.getTwoFactorAppCode())) {
            refreshTokenService.addOrUpdateAttemptsBySubAndIP(sub, deviceIp);
            throw new BadOperationException(ExceptionConstants.INVALID_TWO_FACTOR_CODE);
        }

        // If the code is valid, reset the attempts
        refreshTokenService.resetAttemptsBySub(sub);

        refreshTokenService.addIPToToken(tokenId, deviceIp);
        return ResponseEntity.ok(Map.of("message", SuccessConstants.NEW_IP_ADDED));
    }

    @PostMapping(value = "/update-user-agent-to-session", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<?> updateUserAgentToSession(
            @RequestHeader("X-Device-User-Agent") String deviceUserAgent,
            @RequestParam("code") Integer code,
            @RequestParam("sub") String sub,
            @RequestBody RefreshTokenRequest request
    ) {
        List<String> suspiciousUserAgents = refreshTokenService.getSuspiciousUserAgentsBySub(sub);
        suspiciousUserAgents.stream().filter(userAgent -> userAgent.equals(deviceUserAgent)).findFirst().ifPresent(userAgent -> {
            throw new CriticalSecurityException(ExceptionConstants.SUSPICIOUS_USER_AGENT_DETECTED);
        });

        // UserResponse user = userService.getUserById(sub);
        Status status = userService.getStatusById(sub);

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        String tokenId = refreshTokenService.getClaimFromToken(request.refreshToken(), "token_id");

        Integer tries = this.refreshTokenService.getTriesBySub(sub);

        if (tries >= MAX_ATTEMPTS_BY_TWO_FACTOR) {
            throw new CriticalSecurityException(ExceptionConstants.SEVERAL_FAILED_TWO_FACTOR_ATTEMPTS);
        }

        String secret = twoFactorUserService.getTwoFactorAppSecretByUserId(sub);

        if (secret == null || secret.isEmpty()) {
            throw new BadOperationException(ExceptionConstants.TWO_FACTOR_APP_NOT_ENABLED);
        }

        if (twoFactorService.isCodeInvalid(secret, code)) {
            refreshTokenService.addOrUpdateAttemptsBySubAndUserAgent(sub, deviceUserAgent);
            throw new BadOperationException(ExceptionConstants.INVALID_TWO_FACTOR_CODE);
        }

        // If the code is valid, reset the attempts
        refreshTokenService.resetAttemptsBySub(sub);

        refreshTokenService.updateUserAgentIntoToken(tokenId, deviceUserAgent);
        return ResponseEntity.ok(Map.of("message", SuccessConstants.NEW_USER_AGENT_ADDED));
    }

    @GetMapping(value = "/get-two-factor-status-by-sub", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@securityService.hasAnyAccess({'TWO_FACTOR'})")
    public ResponseEntity<TwoFactorStatus> getAllTwoFactorBySub() {
        PrincipalUser principal = userDetailsService.getUserFromAuth();
        Status status = userService.getStatusById(principal.getSub());

        if (status == null || status == Status.BLOCKED) {
            throw new CriticalSecurityException(ExceptionConstants.USER_TEMPORARY_BLOCKED);
        }

        TwoFactorStatus payload = twoFactorUserService.getAllTwoFactorBySub(principal.getSub());
        return ResponseEntity.ok(payload);
    }

    private final TwoFactorService twoFactorService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final ContactUSUC contactService;
    private final TwoFactorUserUC twoFactorUserService;
    private final UserDetailsServiceImpl userDetailsService;
}