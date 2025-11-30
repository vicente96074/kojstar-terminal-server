package com.kojstarinnovations.terminal.commons.data.dto.commons;

import com.kojstarinnovations.terminal.commons.data.enums.Gender;
import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.IssuingAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * IdentificationDTO - Data Transfer Object for Identification
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdentificationDTO {
    private String transactionId;
    private String identificationNumber;
    private IdentificationType identificationType;
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
    private CountryCodeISO nationality;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private IssuingAuthority issuingAuthority;
    private String imageUrl;
}