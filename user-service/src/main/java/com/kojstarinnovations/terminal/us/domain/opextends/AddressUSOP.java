package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.AddressUSDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AddressUSResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

/**
 * AddressUSOP - Output Port for AddressUS
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface AddressUSOP extends OutputPort<AddressUSDTO, AddressUSResponse, String> {
}