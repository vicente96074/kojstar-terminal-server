package com.kojstarinnovations.terminal.oauth2.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GoogleUserResponse;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GoogleUserDTO;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

import java.util.Optional;

public interface GoogleUserOP extends OutputPort<GoogleUserDTO, GoogleUserResponse, String> {

    Optional<GoogleUserResponse> getByEmail(String email);

    boolean existsByEmail(String email);
}
