package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.IdentificationUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.IdentificationUSResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

/**
 * IdentificationUSOP - Output Port for IdentificationUS
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface IdentificationUSOP extends OutputPort<IdentificationUSDTO, IdentificationUSResponse, String> {

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
            IdentificationType idType,
            CountryCodeISO nationality
    );
}