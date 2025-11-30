package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when there is not enough of a resource
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class NotEnoughException extends RuntimeException {

    /**
     * Constructor for the not enough exception
     *
     * @param message the message to be displayed
     */
    public NotEnoughException(String message) {
        super(message);
    }
}