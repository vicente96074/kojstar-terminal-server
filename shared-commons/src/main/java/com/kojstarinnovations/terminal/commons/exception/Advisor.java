package com.kojstarinnovations.terminal.commons.exception;

import com.kojstarinnovations.terminal.commons.data.enums.ExceptionType;
import com.kojstarinnovations.terminal.commons.exception.response.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.lang.IllegalAccessException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ControllerAdvisor to handle exceptions
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class Advisor extends ResponseEntityExceptionHandler {

    /**
     * Handle exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.RUNTIME)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.NULL_POINTER)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle illegal argument exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.ILLEGAL_ARGUMENT)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle illegal state exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<ExceptionResponse> handleIllegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.ILLEGAL_STATE)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle unsupported operation exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public final ResponseEntity<ExceptionResponse> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.UNSUPPORTED_OPERATION)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle authentication exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.AUTHENTICATION)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle bad credentials exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.BAD_CREDENTIALS)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TwoFactorAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> handleTwoFactorAuthenticationException(TwoFactorAuthenticationException ex) { // 501 status
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.TWO_FACTOR_AUTHENTICATION)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle a bad request exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ExceptionResponse> handleIOException(IOException ex) {
        //ex.printStackTrace();
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.IO_EXCEPTION)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle bad operation exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(BadOperationException.class)
    public final ResponseEntity<ExceptionResponse> handleBadOperationException(BadOperationException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.UNSUPPORTED_OPERATION)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle duplicate exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(DuplicateException.class)
    public final ResponseEntity<ExceptionResponse> handleDuplicateException(DuplicateException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.DUPLICATE)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle file not found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleFileNotFoundException(FileNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.FILE_NOT_FOUND)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handle illegal access exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(IllegalAccessException.class)
    public final ResponseEntity<ExceptionResponse> handleIllegalAccessException(IllegalAccessException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.ILLEGAL_ACCESS)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle invalid data exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidDataException(InvalidDataException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.VALIDATION)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle jasper report exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(JasperReportException.class)
    public final ResponseEntity<ExceptionResponse> handleJasperReportException(JasperReportException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.JASPER_REPORT)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle mapper exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(MapperException.class)
    public final ResponseEntity<ExceptionResponse> handleMapperException(MapperException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.MAPPER)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle no data found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(NoDataFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNoDataFoundException(NoDataFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.NOT_FOUND)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handle not enough exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(NotEnoughException.class)
    public final ResponseEntity<ExceptionResponse> handleNotEnoughException(NotEnoughException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.NO_ENOUGH)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.BAD_REQUEST)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle not found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.NOT_FOUND)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handle path not found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(PathNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlePathNotFoundException(PathNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.PATH_NOT_FOUND)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonalizedException.class)
    public final ResponseEntity<ExceptionResponse> handlePersonalizedException(PersonalizedException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.PERSONALIZED)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle user not found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.USER_NOT_FOUND)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handle validate exception response entity.
     *
     * @param ex the exception to handle
     * @return the response entity
     */
    @ExceptionHandler(ValidateException.class)
    public final ResponseEntity<ExceptionResponse> handleValidateException(ValidateException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.VALIDATION)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CriticalSecurityException.class)
    public final ResponseEntity<ExceptionResponse> handleCriticalSecurityException(CriticalSecurityException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.CRITICAL_SECURITY)
                        .details(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle method argument not valid response entity.
     *
     * @param ex      the ex
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(LocalDateTime.now())
                        .type(ExceptionType.METHOD_ARGUMENT_NOT_VALID)
                        .details(errors.toString())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

}
