package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

import java.util.Optional;

/**
 * RolOP - Output Port for Rol
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface RolOP extends OutputPort<RolDTO, RolResponse, String> {

    /**
     * Get Rol by Rol Name
     *
     * @param rolName Rol Name
     * @return Rol DTO
     */
    Optional<RolResponse> getByRolName(RolName rolName);

    /**
     * Check if Rol exists by Rol Name
     *
     * @param rolName Rol Name
     * @return Boolean
     */
    boolean existsByRolName(RolName rolName);
}