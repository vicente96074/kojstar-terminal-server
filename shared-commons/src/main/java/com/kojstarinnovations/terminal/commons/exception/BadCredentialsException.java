package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when the credentials provided are invalid
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class BadCredentialsException extends RuntimeException {

    /**
     * Constructor for the bad credentials exception
     *
     * @param message the message to be displayed
     */
    public BadCredentialsException(String message) {
        super(message);
    }
}