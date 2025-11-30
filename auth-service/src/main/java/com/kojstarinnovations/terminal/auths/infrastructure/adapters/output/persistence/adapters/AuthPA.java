package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.auths.domain.opextends.AuthOP;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity.User;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.repository.AuthRepository;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * AuthPA - Persistence Adapter for Auth
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class AuthPA implements AuthOP {

    /**
     * Check if a user exists by username
     *
     * @param username - Username to be checked
     * @return boolean - True if the user exists, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    /**
     * Check if a user exists by email
     *
     * @param email - Email to be checked
     * @return boolean - True if the user exists, false otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<UserResponse> getByEmail(String email) {
        return repository.findByEmail(email)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build());
    }

    private final AuthRepository repository;
}