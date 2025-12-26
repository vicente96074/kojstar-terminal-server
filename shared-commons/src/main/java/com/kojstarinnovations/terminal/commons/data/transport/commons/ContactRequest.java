package com.kojstarinnovations.terminal.commons.data.transport.commons;

import com.kojstarinnovations.terminal.commons.data.constants.ValidationConstants;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.SQLInjectionMalicious;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.CONTACT_TYPE_REQUIRED)
    private String contactType; // = ContactType.EMAIL.name();

    //Phone only
    private String countryCode; // = CountryCodeISO.GT.getCode(); // Default to Guatemala (Map on manually)
    private String regionCode; // = RegionCodeISO.GT_QZ.getCode(); // Default to Guatemala - Quetzaltenango (Map on manually)
    private String phoneNumber; //

    //Email only
    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String email;

    //Who is the primary contact
    private Boolean primaryContact;
}