package com.kojstarinnovations.terminal.commons.data.enums;

import lombok.Getter;

/**
 * ExceptionType - Enum for Exception Type
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Getter
public enum ExceptionType {
    RUNTIME("Runtime"),
    NULL_POINTER("Null pointer"),
    ILLEGAL_ARGUMENT("Illegal argument"),

    USERNAME_NOT_FOUND("Username not found"),

    AUTHENTICATION("Authentication"),
    BAD_CREDENTIALS("Bad credentials"),
    UNSUPPORTED_OPERATION("Unsupported operation"),
    VALIDATION("Validation"),
    METHOD_ARGUMENT_NOT_VALID("Method argument not valid"),
    DUPLICATE("Duplicate"),
    FILE_NOT_FOUND("File not found"),
    ILLEGAL_ACCESS("Illegal access"),
    JASPER_REPORT("Jasper report"),
    MAPPER("Mapper"),
    NOT_FOUND("Not found"),
    PATH_NOT_FOUND("Path not found"),
    NO_ENOUGH("No enough"),
    USER_NOT_FOUND("User not found"),

    MALFORMED_JWT("Malformed JWT"),
    UNSUPPORTED_JWT("Unsupported JWT"),
    EXPIRED_JWT("Expired JWT"),
    SIGNATURE_JWT("Signature JWT"),
    CRITICAL_SECURITY("Critical security"),
    SERVICE_UNAVAILABLE("Service unavailable"),
    BAD_REQUEST("Bad request"),
    ILLEGAL_STATE("Illegal state"),
    IO_EXCEPTION("IO exception"),
    TWO_FACTOR_AUTHENTICATION("Two factor authentication"),
    PERSONALIZED("Personalized"),
    ;

    ExceptionType(String type) {
        this.type = type;
    }

    private final String type;
}