package com.kojstarinnovations.terminal.commons.data.transport.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * ForgotPasswordRequest - Transport for Forgot Password with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class ForgotPasswordRequest {
    private String email;
    private String clientUrl;
    private String languageTag;
}