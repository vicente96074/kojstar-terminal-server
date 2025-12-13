package com.kojstarinnovations.terminal.st.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.BranchOfficeResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;
import com.kojstarinnovations.terminal.st.domain.model.BranchOfficeDTO;

public interface BranchOfficeOP extends OutputPort<BranchOfficeDTO, BranchOfficeResponse, String> {
}