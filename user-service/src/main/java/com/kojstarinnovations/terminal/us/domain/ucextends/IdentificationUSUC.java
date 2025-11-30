package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.IdentificationUSResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.us.application.data.request.IdentificationUSRequest;

/**
 * IdentificationUSUC - Interface that handles Identification use cases, including saving, deleting, retrieving, and updating Identification.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface IdentificationUSUC extends UseCase<IdentificationUSRequest, IdentificationUSResponse, String> {

    /**
     * Check if entity exists by identification number and identification type
     *
     * @param idNumber    the identification number
     * @param idType      the identification type
     * @param nationality the nationality
     * @return boolean
     */
    boolean existsByIDNumberAndIDTypeAndNationality(
            String idNumber,
            String idType,
            String nationality
    );
}