package com.kojstarinnovations.terminal.commons.data.transport.authservice;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * ResetPasswordRequest - Transport object for Reset Password
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResetPasswordRequest extends CommonsRequest {
    private String token;
    private String password;
    private String confirmPassword;
    private String code;
}