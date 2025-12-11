package com.kojstarinnovations.terminal.auths.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.authservice.ForgotPasswordDTO;
import com.kojstarinnovations.terminal.commons.data.payload.authentication.ForgotPasswordResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

import java.util.Optional;

/**
 * ForgotPasswordOP - Output Port for ForgotPassword
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface ForgotPasswordOP extends OutputPort<ForgotPasswordDTO, ForgotPasswordResponse, Long> {

    /**
     * Method to find a modelDto by token
     *
     * @param token the token to be searched
     * @return modelDto with the given token
     */
    Optional<ForgotPasswordResponse> findByToken(String token);

    /**
     * Method to consume a token
     *
     * @param token the token to be consumed
     */
    void consumeToken(String token);
}