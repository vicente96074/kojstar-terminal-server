package com.kojstarinnovations.terminal.commons.data.payload.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ForgotPasswordResponse {
    private String email;
    private String username;
    private String userId;
    private String token;
    private String code;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private Boolean used;
    private String clientUrl;
}