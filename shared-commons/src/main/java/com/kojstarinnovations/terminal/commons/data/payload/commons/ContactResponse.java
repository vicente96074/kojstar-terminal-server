package com.kojstarinnovations.terminal.commons.data.payload.commons;

import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    private String transactionId;
    private ContactType contactType;
    private String countryCode;
    private String regionCode;
    private String phoneNumber;
    private String email;
    private Boolean primaryContact;
}