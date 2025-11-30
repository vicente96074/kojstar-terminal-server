package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;
import com.kojstarinnovations.terminal.us.domain.model.UserAccessDTO;
import com.kojstarinnovations.terminal.us.domain.model.UserAccessIDDTO;

/**
 * UserAccessOP - Output Port for UserAccess
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface UserAccessOP extends OutputPort<UserAccessDTO, UserAccessIDDTO> {

    /**
     * Delete User Access by User Id
     *
     * @param userId User Id
     */
    void deleteByUserId(String userId);
}