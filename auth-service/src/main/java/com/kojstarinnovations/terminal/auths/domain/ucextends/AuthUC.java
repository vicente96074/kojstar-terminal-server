package com.kojstarinnovations.terminal.auths.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;

import java.util.Optional;

/**
 * AuthUC - Use Case Interface for Authentication
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AuthUC {

    Optional<UserResponse> getByEmail(String email);

}