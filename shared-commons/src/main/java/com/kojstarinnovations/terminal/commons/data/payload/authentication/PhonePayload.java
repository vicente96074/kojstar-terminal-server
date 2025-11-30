package com.kojstarinnovations.terminal.commons.data.payload.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhonePayload {
    private String id;
    private String userId;
    private String countryCode;
    private String phoneNumber;
}