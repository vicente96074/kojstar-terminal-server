package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a critical security error occurs
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class CriticalSecurityException extends RuntimeException {

    /**
     * Constructor for the critical security exception
     *
     * @param message the message to be displayed
     */
    public CriticalSecurityException(String message) {
        super(message);
    }
}