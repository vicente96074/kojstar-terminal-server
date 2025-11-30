package com.kojstarinnovations.terminal.auths.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;

import java.util.Optional;

/**
 * UserOP - Output Port for User
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AuthOP {

    /**
     * Check if a user exists by username
     *
     * @param username - Username to be checked
     * @return boolean - True if the user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user exists by email
     *
     * @param email - Email to be checked
     * @return boolean - True if the user exists, false otherwise
     */
    boolean existsByEmail(String email);

    Optional<UserResponse> getByEmail(String email);
}