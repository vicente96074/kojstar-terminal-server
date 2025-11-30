package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when an operation is not allowed
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class BadOperationException extends RuntimeException {

    /**
     * Constructor for the bad operation exception
     *
     * @param message the message to be displayed
     */
    public BadOperationException(String message) {
        super(message);
    }
}