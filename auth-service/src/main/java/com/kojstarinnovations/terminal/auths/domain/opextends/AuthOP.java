package com.kojstarinnovations.terminal.auths.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;

import java.util.Optional;

/**
 * UserOP - Output Port for User
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AuthOP {
    Optional<UserResponse> getByEmail(String email);
}