package com.kojstarinnovations.terminal.commons.exception.response;

import com.kojstarinnovations.terminal.commons.data.enums.ExceptionType;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * ExceptionResponse: Exception response object
 *
 * @param date
 * @param type
 * @param details
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Builder
public record ExceptionResponse(
        LocalDateTime date,
        ExceptionType type,
        String details
) {

}