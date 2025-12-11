package com.kojstarinnovations.terminal.oauth2.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GithubUserResponse;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GithubUserDTO;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

import java.util.Optional;

public interface GithubUserOP extends OutputPort<GithubUserDTO, GithubUserResponse, String> {
    Optional<GithubUserResponse> findByEmail(String email);

    boolean existsByEmail(String email);
}