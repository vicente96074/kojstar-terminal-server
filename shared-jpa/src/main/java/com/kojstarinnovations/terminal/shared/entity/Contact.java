package com.kojstarinnovations.terminal.shared.entity;

import com.kojstarinnovations.terminal.commons.data.enums.ContactType;

import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.shared.coverters.enums.ContactTypeConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.CountryCodeISOConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.RegionCodeISOConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Column(name = "contact_type", nullable = false, columnDefinition = "VARCHAR(12) DEFAULT 'NONE'")
    @Convert(converter = ContactTypeConverter.class)
    private ContactType contactType;

    //Form phone only
    @Column(name = "country_code", columnDefinition = "VARCHAR(2) DEFAULT 'GT'")
    @Convert(converter = CountryCodeISOConverter.class)
    private CountryCodeISO countryCodeISO;

    @Column(name = "region_code", columnDefinition = "VARCHAR(5) DEFAULT 'GT-QZ'")
    @Convert(converter = RegionCodeISOConverter.class)
    private RegionCodeISO regionCodeISO;

    @Column(name = "phone_number")
    private String phoneNumber;

    //Form email only
    @Column(name = "email")
    private String email;

    @Column(name = "primary_contact", nullable = false)
    private Boolean primaryContact;
}