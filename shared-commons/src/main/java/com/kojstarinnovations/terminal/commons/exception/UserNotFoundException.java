package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a user is not found
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructor for the user not found exception
     *
     * @param message the message to be displayed
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}