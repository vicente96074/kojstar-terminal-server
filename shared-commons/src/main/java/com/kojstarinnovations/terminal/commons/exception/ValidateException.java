package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a validation fails
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class ValidateException extends RuntimeException {

    /**
     * Constructor for the validation exception
     *
     * @param message the message to be displayed
     */
    public ValidateException(String message) {
        super(message);
    }
}