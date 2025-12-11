package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.us.application.data.request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * UserUC - Interface that handles User use cases, including saving, deleting, retrieving, and updating User.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface UserUC extends UseCase<UserRequest, UserResponse, String> {

    /**
     * Exists by username
     *
     * @param username the username to search
     * @return boolean exists otherwise false
     */
    boolean existsByUsername(String username);

    /**
     * Exists by email
     *
     * @param email the email to search
     * @return boolean exists otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * Get page active by store id
     *
     * @param pageable the page to search
     * @return page of user response
     */
    Page<UserResponse> getActiveStatusPage(Pageable pageable);

    /**
     * Block user
     *
     * @param userId the id of the user
     */
    void blockUser(String userId);

    /**
     * Get status by user id
     *
     * @param userId the user ID
     * @return Status the status
     */
    Status getStatusById(String userId);
}