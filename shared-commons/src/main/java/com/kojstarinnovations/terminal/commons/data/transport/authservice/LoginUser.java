package com.kojstarinnovations.terminal.commons.data.transport.authservice;

import com.kojstarinnovations.terminal.commons.data.constants.ValidationConstants;
import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.SQLInjectionMalicious;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends CommonsRequest {

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = "Username es requerido")
    private String username;

    @SQLInjectionMalicious(message = ValidationConstants.SQL_INJECTION_MALICIOUS)
    @DataRequired(message = "Password es requerido")
    private String password;
}
