package com.kojstarinnovations.terminal.commons.exception;

/**
 * Exception thrown when a Jasper Report fails
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class JasperReportException extends RuntimeException {

    /**
     * Constructor for the Jasper Report exception
     *
     * @param message the message to be displayed
     */
    public JasperReportException(String message) {
        super(message);
    }
}