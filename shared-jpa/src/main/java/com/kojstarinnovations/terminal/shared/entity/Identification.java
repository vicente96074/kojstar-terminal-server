package com.kojstarinnovations.terminal.shared.entity;

import com.kojstarinnovations.terminal.commons.data.enums.Gender;
import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.IssuingAuthority;
import com.kojstarinnovations.terminal.shared.coverters.enums.CountryCodeISOConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.GenderConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.IdentificationTypeConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.IssuingAuthorityConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Identification - Structure of the Identification entity, this is a mapped superclass and is extended by other entities.
 * Identification type can be: DPI, PASSPORT, LICENSE, etc.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Identification {

    @Column(name = "identification_number", nullable = false)
    private String identificationNumber;

    @Column(name = "identification_type", nullable = false, columnDefinition = "VARCHAR(18) DEFAULT 'DPI'")
    @Convert(converter = IdentificationTypeConverter.class)
    private IdentificationType identificationType = IdentificationType.DPI;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", columnDefinition = "VARCHAR(15) DEFAULT 'UNSPECIFIED'")
    @Convert(converter = GenderConverter.class)
    private Gender gender = Gender.UNSPECIFIED;

    @Column(name = "nationality", columnDefinition = "VARCHAR(2) DEFAULT 'GT'")
    @Convert(converter = CountryCodeISOConverter.class)
    private CountryCodeISO nationality = CountryCodeISO.GT;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate = LocalDate.now();

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "issuing_authority", columnDefinition = "VARCHAR(12) DEFAULT 'RENAP'")
    @Convert(converter = IssuingAuthorityConverter.class)
    private IssuingAuthority issuingAuthority = IssuingAuthority.RENAP;

    @Column(name = "image_url")
    private String imageUrl;
}