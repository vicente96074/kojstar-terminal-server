package com.kojstarinnovations.terminal.auths.domain.service;

import com.kojstarinnovations.terminal.auths.domain.opextends.AuthOP;
import com.kojstarinnovations.terminal.auths.domain.ucextends.AuthUC;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * AuthService - Implementation of the Auth use case interface for authentication purposes.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService implements AuthUC {

    @Override
    public Optional<UserResponse> getByEmail(String email){
        return outputPort.getByEmail(email);
    }

    private final AuthOP outputPort;
}