package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.exception;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.enums.ExceptionType;
import com.kojstarinnovations.terminal.commons.exception.Advisor;
import com.kojstarinnovations.terminal.commons.exception.response.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor extends Advisor {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.SQL_EXCEPTION)
                        .details(I18nCommonConstants.EXCEPTION_SQL_DATA_INTEGRITY_VIOLATION)
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}