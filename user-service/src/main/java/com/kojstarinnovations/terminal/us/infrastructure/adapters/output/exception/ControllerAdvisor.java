package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.exception;

import com.kojstarinnovations.terminal.commons.data.enums.ExceptionType;
import com.kojstarinnovations.terminal.commons.exception.Advisor;
import com.kojstarinnovations.terminal.commons.exception.response.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

/**
 * ControllerAdvisor - Controller Advisor, responsible for handling exceptions
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@ControllerAdvice
public class ControllerAdvisor extends Advisor {

    /**
     * Handle io exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(MalformedJwtException.class)
    public final ResponseEntity<ExceptionResponse> handleMalformedJwtException(MalformedJwtException ex) {
        LoggerFactory.getLogger(ControllerAdvisor.class).error("MalformedJwtException", ex);
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.MALFORMED_JWT)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle io exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    public final ResponseEntity<ExceptionResponse> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.UNSUPPORTED_JWT)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle io exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.EXPIRED_JWT)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle io exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(SignatureException.class)
    public final ResponseEntity<ExceptionResponse> handleSignatureException(SignatureException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.SIGNATURE_JWT)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}