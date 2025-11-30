package com.kojstarinnovations.terminal.commons.exception;

public class PersonalizedException extends RuntimeException{
    /**
     * Constructor for the personalized exception
     *
     * @param message the message to be displayed
     */
    public PersonalizedException(String message) {
        super(message);
    }

    /**
     * Constructor for the personalized exception with a cause
     *
     * @param message the message to be displayed
     * @param cause   the cause of the exception
     */
    public PersonalizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
