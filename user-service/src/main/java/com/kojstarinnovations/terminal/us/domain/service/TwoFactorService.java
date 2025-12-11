package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.us.domain.ucextends.TwoFactorUC;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwoFactorService implements TwoFactorUC {

    public String generateSecretKey() {
        return googleAuthenticator.createCredentials().getKey();
    }

    public String getOtpAuthURL(String issuer, String accountName, String secret) {
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer,
                accountName,
                secret,
                issuer
        );
    }

    public boolean isCodeInvalid(String twoFactorSecret, int code) {
        return !googleAuthenticator.authorize(twoFactorSecret, code);
    }

    private final GoogleAuthenticator googleAuthenticator;
}
