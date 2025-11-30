package com.kojstarinnovations.terminal.commons.data.enums;

import lombok.Getter;

/**
 * PrefixCodesISO - Enum for prefix codes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Getter
public enum PrefixCodesISO {
    USER_ID("KOJ"),
    ACCESS_ID("AC"),
    ROLE_ID("RO"),
    IDENTIFICATION_ID("ID"),
    ADDRESS_ID("AD"),
    CUSTOMER_ID("CU"),
    TRANSACTION_ID("TR"),
    CONTACT_ID("CO"),

    UST_TRANSACTION_ID("UST"),
    USR_TRANSACTION_ID("USR"),
    SAGA_ID("SAG"),
    USER_SETTING_ID("USTG");
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