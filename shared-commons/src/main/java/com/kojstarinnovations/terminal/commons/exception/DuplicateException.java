package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a duplicate entry is found
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class DuplicateException extends RuntimeException {

    /**
     * Constructor for the duplicate exception
     *
     * @param message the message to be displayed
     */
    public DuplicateException(String message) {
        super(message);
    }
}