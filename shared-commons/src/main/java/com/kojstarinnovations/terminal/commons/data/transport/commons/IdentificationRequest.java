package com.kojstarinnovations.terminal.commons.data.transport.commons;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kojstarinnovations.terminal.commons.data.constants.ValidationConstants;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.SQLInjectionMalicious;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * IdentificationRequest - Transport object for Identification with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationRequest{

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.IDENTIFICATION_NUMBER_REQUIRED)
    private String identificationNumber;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.IDENTIFICATION_TYPE_REQUIRED)
    private String identificationType; // = IdentificationType.DPI.name();

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.FULL_NAME_REQUIRED)
    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String gender; // = Gender.OTHER.name();

    @DataRequired(message = ValidationConstants.NATIONALITY_REQUIRED)
    private String nationalityCode; // = CountryCodeISO.GT.getCode();

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String issuingAuthority; // = IssuingAuthority.RENAP.getCode();

    //
    private String imageUrl;
}