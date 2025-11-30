package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when the data provided is invalid
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructor for the invalid data exception
     *
     * @param message the message to be displayed
     */
    public InvalidDataException(String message) {
        super(message);
    }
}