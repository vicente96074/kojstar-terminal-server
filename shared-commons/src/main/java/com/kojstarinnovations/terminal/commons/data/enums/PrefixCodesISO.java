package com.kojstarinnovations.terminal.commons.data.enums;

import lombok.Getter;

/**
 * PrefixCodesISO - Enum for prefix codes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Getter
public enum PrefixCodesISO {
    STORE_ID("KTR"),

    USER_ID("KOJ"),
    ACCESS_ID("AC"),
    ROLE_ID("RO"),
    IDENTIFICATION_ID("ID"),
    ADDRESS_ID("AD"),
    CONTACT_ID("CO"),

    USR_TRANSACTION_ID("USR");

    private final String code;

    /**
     * Constructor
     *
     * @param code - String
     */
    PrefixCodesISO(String code) {
        this.code = code;
    }
}