package com.kojstarinnovations.terminal.oauth2.domain.service;

import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GithubUserResponse;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GithubUserDTO;
import com.kojstarinnovations.terminal.oauth2.domain.opextends.GithubUserOP;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GithubUserService {

    public boolean existsByEmail(String email) {
        return outputPort.existsByEmail(email);
    }

    public GithubUserResponse findByEmail(String email) {
        return outputPort.findByEmail(email)
                .orElseThrow();
    }

    public GithubUserResponse save(GithubUserDTO dto) {
        return outputPort.save(dto);
    }

    public GithubUserResponse findById(String id) {
        return outputPort.getById(id)
                .orElse(null);
    }

    private final GithubUserOP outputPort;
}
