package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserRolResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.us.application.data.request.UserRolIdRequest;
import com.kojstarinnovations.terminal.us.application.data.request.UserRolRequest;

/**
 * UserRolUC - Interface that handles UserRol use cases, including saving, deleting, retrieving, and updating UserRol.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface UserRolUC extends UseCase<UserRolRequest, UserRolResponse, UserRolIdRequest> {

    /**
     * Deletes all user rols by user id
     *
     * @param userId the user id
     */
    void deleteByUserId(String userId);
}