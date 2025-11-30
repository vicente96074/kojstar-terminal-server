package com.kojstarinnovations.terminal.commons.data.transport.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorCodes {
    private int twoFactorAppCode;
    private String emailCode;
    private String smsCode;
}