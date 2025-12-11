package com.kojstarinnovations.terminal.oauth2.domain.service;

import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GoogleUserResponse;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GoogleUserDTO;
import com.kojstarinnovations.terminal.oauth2.domain.opextends.GoogleUserOP;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleUserService {

    public GoogleUserResponse findByEmail(String email) {
        return outputPort.getByEmail(email)
                .orElse(null);
    }

    public boolean existsByEmail(String email) {
        return outputPort.existsByEmail(email);
    }

    public GoogleUserResponse save(GoogleUserDTO dto) {
        return outputPort.save(dto);
    }

    public GoogleUserResponse findById(String id) {
        return outputPort.getById(id)
                .orElse(null);
    }

    private final GoogleUserOP outputPort;
}