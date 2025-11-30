package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when no data is found
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class NoDataFoundException extends RuntimeException {

    /**
     * Constructor for the no data found exception
     *
     * @param message the message to be displayed
     */
    public NoDataFoundException(String message) {
        super(message);
    }
}