package com.kojstarinnovations.terminal.commons.data.dto.commons;

import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String transactionId;
    private ContactType contactType;
    private CountryCodeISO countryCodeISO;
    private RegionCodeISO regionCodeISO;
    private String phoneNumber;
    private String email;
    private Boolean primaryContact;
}