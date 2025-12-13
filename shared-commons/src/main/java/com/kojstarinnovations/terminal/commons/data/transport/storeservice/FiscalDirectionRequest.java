package com.kojstarinnovations.terminal.commons.data.transport.storeservice;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * FiscalDirectionRequest - Transport object for Fiscal Direction with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FiscalDirectionRequest extends CommonsRequest {

    @DataRequired(message = "Abbrev is required")
    private String abbrev;

    @DataRequired(message = "Identifier number is required")
    private String identifierNumber;

    @DataRequired(message = "Fiscal direction is required")
    private String fiscalDirection;

    @DataRequired(message = "Street is required")
    private String street;

    @DataRequired(message = "City is required")
    private String city;

    @DataRequired(message = "State is required")
    private String state;

    @DataRequired(message = "Country is required")
    private String country;

    @DataRequired(message = "Description is required")
    private String description;
}