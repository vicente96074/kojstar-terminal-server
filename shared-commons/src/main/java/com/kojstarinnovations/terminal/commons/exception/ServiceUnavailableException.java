package com.kojstarinnovations.terminal.commons.exception;

import lombok.Getter;

@Getter
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message, String serviceName) {
        super(message);
        this.serviceName = serviceName;
    }

    private final String serviceName;
}
