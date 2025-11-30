package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.RolRequest;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;

/**
 * RolUC - Interface that handles Rol use cases, including saving, deleting, retrieving, and updating Rol.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface RolUC extends UseCase<RolRequest, RolResponse, String> {

    /**
     * getByRolName
     *
     * @param rolName the rol name to search
     * @return rol response search result
     */
    RolResponse getByRolName(RolName rolName);

    /**
     * existsByRolName
     *
     * @param rolName the rol name to search
     * @return true if the rol exists, false otherwise
     */
    boolean existsByRolName(RolName rolName);
}
