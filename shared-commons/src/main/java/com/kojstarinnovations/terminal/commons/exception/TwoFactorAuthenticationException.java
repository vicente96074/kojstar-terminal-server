package com.kojstarinnovations.terminal.commons.exception;

public class TwoFactorAuthenticationException extends RuntimeException {
    public TwoFactorAuthenticationException(String message) {
        super(message);
    }
}