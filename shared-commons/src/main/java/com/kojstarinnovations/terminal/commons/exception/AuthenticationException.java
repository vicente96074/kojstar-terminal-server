package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when an authentication error occurs
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class AuthenticationException extends RuntimeException {

    /**
     * Constructor for the authentication exception
     *
     * @param message the message to be displayed
     * @param cause   the cause of the exception
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for the authentication exception
     *
     * @param message the message to be displayed
     */
    public AuthenticationException(String message) {
        super(message);
    }
}