package com.kojstarinnovations.terminal.commons.data.transport.commons;

import com.kojstarinnovations.terminal.commons.data.constants.ValidationConstants;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.SubdivisionRegionCodeISO;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.SQLInjectionMalicious;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * AddressRequest - Transport object for address requests with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressRequest extends CommonsRequest {

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = ValidationConstants.ADDRESS_REQUIRED)
    private String address;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String city;

    @DataRequired(message = ValidationConstants.SUBDIVISION_REGION_CODE_REQUIRED)
    private String subdivisionRegionCode = SubdivisionRegionCodeISO.GT_QZ_QU.getCode(); // Default to Quezaltenango(Xela)

    @DataRequired(message = ValidationConstants.REGION_CODE_REQUIRED)
    private String regionCode = RegionCodeISO.GT_QZ.getCode(); // Default to Quezaltenango

    @DataRequired(message = ValidationConstants.COUNTRY_CODE_ISO_REQUIRED)
    private String countryCode = CountryCodeISO.GT.getCode(); // Default to Guatemala

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    private String postalCode;
    private Integer latitude;
    private Integer longitude;
}