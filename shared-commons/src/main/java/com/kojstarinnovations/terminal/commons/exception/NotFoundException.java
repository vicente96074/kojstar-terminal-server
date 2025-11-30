package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when an object is not found
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructor for the not found exception
     *
     * @param message the message to be displayed
     */
    public NotFoundException(String message) {
        super(message);
    }
}