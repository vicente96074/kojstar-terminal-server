package com.kojstarinnovations.terminal.commons.data.dto.authservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ForgotPasswordDTO - Data Transfer Object for Forgot Password
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ForgotPasswordDTO {
    private String email;
    private String username;
    private String userId;
    private String token;
    private String code;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;
    private Boolean used;
    private String clientUrl;
}