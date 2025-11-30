package com.kojstarinnovations.terminal.auths.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.transport.mail.ForgotPasswordRequest;
import com.kojstarinnovations.terminal.commons.exception.BadRequestException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ValidatorRequestsService {

    @SneakyThrows
    public void validateForgotPasswordRequest(ForgotPasswordRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new BadRequestException(ExceptionConstants.EMAIL_CANNOT_BE_EMPTY);
        }

        if (request.getClientUrl() == null || request.getClientUrl().isEmpty()) {
            throw new BadRequestException(ExceptionConstants.CLIENT_URL_CANNOT_BE_EMPTY);
        }

    }
}
