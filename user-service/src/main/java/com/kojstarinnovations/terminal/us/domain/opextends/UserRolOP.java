package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserRolResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;
import com.kojstarinnovations.terminal.us.domain.model.UserRolDTO;
import com.kojstarinnovations.terminal.us.domain.model.UserRolIDDTO;

/**
 * UserRolOP - Output Port for UserRol
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface UserRolOP extends OutputPort<UserRolDTO, UserRolResponse, UserRolIDDTO> {

    /**
     * Check if entity exists by UserRol ID
     *
     * @param id the UserRol ID
     * @return boolean
     */
    boolean existsById(UserRolIDDTO id);

    /**
     * Delete User Rol by User Id
     *
     * @param userId User Id
     */
    void deleteByUserId(String userId);
}