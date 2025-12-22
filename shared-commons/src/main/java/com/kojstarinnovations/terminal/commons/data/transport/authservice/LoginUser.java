package com.kojstarinnovations.terminal.commons.data.transport.authservice;

import com.kojstarinnovations.terminal.commons.data.constants.ValidationConstants;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.SQLInjectionMalicious;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * LoginUser - Transport object for Login User with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = "Username es requerido")
    private String username;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = "Password es requerido")
    private String password;
}
