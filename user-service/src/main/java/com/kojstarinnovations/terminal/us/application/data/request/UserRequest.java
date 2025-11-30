package com.kojstarinnovations.terminal.us.application.data.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kojstarinnovations.terminal.commons.data.constants.ValidationConstants;
import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UserRequest - Transport object for user requests
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends CommonsRequest {

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.FIRST_NAME_REQUIRED)
    private String firstName;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String middleName;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.FIRST_SURNAME_REQUIRED)
    private String firstSurname;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String secondSurname;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String marriageSurname;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.USERNAME_REQUIRED)
    private String username;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.EMAIL_REQUIRED)
    @Email(message = ValidationConstants.EMAIL_NOT_VALID)
    private String email;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @PasswordRequired(message = ValidationConstants.PASSWORD_REQUIRED)
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.STORE_BRANCH_ID_REQUIRED)
    private String storeBranchId;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.STORE_ID_REQUIRED)
    private String storeId;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String userSettingId;

    private Set<String> rolesRequest = new HashSet<>();
    private Set<String> accessesRequest = new HashSet<>();

    @Valid
    @RequestClassRequired(message = ValidationConstants.IDENTIFICATION_REQUEST_REQUIRED)
    private IdentificationUSRequest identificationUSRequest;

    @Valid
    @ListOfRequestClassRequired(message = ValidationConstants.CONTACT_REQUEST_REQUIRED)
    private List<ContactUSRequest> contactUSRequests;

    @Valid
    @RequestClassRequired(message = ValidationConstants.ADDRESS_REQUEST_REQUIRED)
    private AddressUSRequest addressUSRequest;
}