package com.kojstarinnovations.terminal.commons.data.dto.commons;

import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorDTO {
    private TwoFactorType twoFactorType;
    private Boolean twoFactorEnabled;
    private Boolean twoFactorActive;
    private String twoFactorSecret;
    private String twoFactorPhone;
    private CountryCodeISO twoFactorCountryCode;
    private String twoFactorEmail;
    private Boolean isPrimary;
}