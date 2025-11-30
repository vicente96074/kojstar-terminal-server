package com.kojstarinnovations.terminal.commons.data.payload.commons;

import com.kojstarinnovations.terminal.commons.data.enums.Gender;
import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.IssuingAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * IdentificationResponse - Payload for Identification,
 * type is an enum with the following values: "PASSPORT", "ID_CARD", "DRIVER_LICENSE"
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdentificationResponse {
    private String identificationNumber;
    private IdentificationType identificationType;
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
    private String nationalityCode;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private IssuingAuthority issuingAuthority;
    private String imageUrl;
}