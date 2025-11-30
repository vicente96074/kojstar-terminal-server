package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorStatus {
    private boolean appEnabled;
    private boolean appActive;
    private boolean smsEnabled;
    private boolean smsActive;
    private String phoneNumber;
    private String phoneNumberCountryCode;
    private boolean emailEnabled;
    private boolean emailActive;
    private String email;
}