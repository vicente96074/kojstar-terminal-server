package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

import java.util.Optional;

/**
 * AccessOP - Output Port for Access
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AccessOP extends OutputPort<AccessDTO, AccessResponse, String> {

    /**
     * Get Access by Access Name
     *
     * @param accessName Access Name
     * @return Access DTO
     */
    Optional<AccessResponse> getByAccessName(AccessName accessName);

    /**
     * Check if Access exists by Access Name
     *
     * @param accessName Access Name
     * @return Boolean
     */
    boolean existsByAccessName(AccessName accessName);
}