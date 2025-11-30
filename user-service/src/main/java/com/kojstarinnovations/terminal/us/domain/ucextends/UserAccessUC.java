package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserAccessResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.us.application.data.request.UserAccessRequest;

/**
 * UserAccessUC - Interface that handles User Access use cases, including saving, deleting, retrieving, and updating User Access.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface UserAccessUC extends UseCase<UserAccessRequest, UserAccessResponse, String> {

    /**
     * Delete entity by id
     *
     * @param userId the id of the entity to be deleted
     */
    void deleteByUserId(String userId);
}