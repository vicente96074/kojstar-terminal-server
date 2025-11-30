package com.kojstarinnovations.terminal.us.domain.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public String generateSecretKey() {
        return gAuth.createCredentials().getKey();
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
        return !gAuth.authorize(twoFactorSecret, code);
    }
}
