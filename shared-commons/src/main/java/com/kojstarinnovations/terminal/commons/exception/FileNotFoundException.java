package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a file is not found
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class FileNotFoundException extends RuntimeException {

    /**
     * Constructor for the file not found exception
     *
     * @param message the message to be displayed
     */
    public FileNotFoundException(String message) {
        super(message);
    }
}