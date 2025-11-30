package com.kojstarinnovations.terminal.commons.data.dto.authservice;

import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorCode {
    private Integer code;
    private String sub;
    private Instant issuedAt;
    private Instant expiration;
    private Boolean used;
    private TwoFactorType type;
    private String phoneNumber;
    private String email;
}