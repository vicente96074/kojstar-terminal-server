package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a mapper fails
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class MapperException extends RuntimeException {

    /**
     * Constructor for the mapper exception
     *
     * @param message the message to be displayed
     */
    public MapperException(String message) {
        super(message);
    }
}