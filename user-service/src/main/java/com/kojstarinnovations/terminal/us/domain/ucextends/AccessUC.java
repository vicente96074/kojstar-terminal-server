package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.AccessRequest;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;

/**
 * AccessUC - Interface that handles Access use cases, including saving, deleting, retrieving, and updating Access.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AccessUC extends UseCase<AccessRequest, AccessResponse, String> {

    /**
     * Get Access by Access Name
     *
     * @param accessName the Access Name
     * @return AccessResponse
     */
    AccessResponse getByAccessName(AccessName accessName);

    /**
     * Check if entity exists by Access Name
     *
     * @param accessName the Access Name
     * @return boolean
     */
    boolean existsByAccessName(AccessName accessName);
}