package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.exception;

import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.data.enums.ExceptionType;
import com.kojstarinnovations.terminal.commons.exception.Advisor;
import com.kojstarinnovations.terminal.commons.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * ControllerAdvisor - This class is responsible for handling exceptions in the controller layer.
 * It is responsible for handling exceptions such as UsernameNotFoundException, BadCredentialsException and CriticalSecurityException.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@ControllerAdvice
public class ControllerAdvisor extends Advisor {

    /**
     * Handle the UsernameNotFoundException exception
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.USER_NOT_FOUND)
                        .details(I18nAuthConstants.EXCEPTION_USERNAME_NOT_FOUND)
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handle the BadCredentialsException exception
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.BAD_CREDENTIALS)
                        .details(I18nAuthConstants.EXCEPTION_USERNAME_OR_PASSWORD_INVALID)
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }
}