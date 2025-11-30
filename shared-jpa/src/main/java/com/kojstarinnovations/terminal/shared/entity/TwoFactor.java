package com.kojstarinnovations.terminal.shared.entity;

import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.shared.coverters.enums.CountryCodeISOConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.TwoFactorTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactor {

    @Column(name = "two_factor_type")
    @Convert(converter = TwoFactorTypeConverter.class)
    private TwoFactorType twoFactorType;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled;

    @Column(name = "two_factor_active")
    private Boolean twoFactorActive;

    @Column(name = "two_factor_phone")
    private String twoFactorPhone; // SMS

    @Column(name = "country_code")
    @Convert(converter = CountryCodeISOConverter.class)
    private CountryCodeISO twoFactorCountryCode;

    @Column(name = "two_factor_email")
    private String twoFactorEmail; // Email

    @Column(name = "two_factor_primary")
    private Boolean isPrimary; // Indicates if this is the primary two-factor method
}