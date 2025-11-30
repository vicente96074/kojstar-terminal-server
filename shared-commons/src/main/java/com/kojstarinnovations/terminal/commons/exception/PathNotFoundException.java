package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a path is not found
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class PathNotFoundException extends RuntimeException {

    /**
     * Constructor for the path not found exception
     *
     * @param message the message to be displayed
     */
    public PathNotFoundException(String message) {
        super(message);
    }
}