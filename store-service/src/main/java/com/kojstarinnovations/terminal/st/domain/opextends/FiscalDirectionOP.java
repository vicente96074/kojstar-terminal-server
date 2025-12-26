package com.kojstarinnovations.terminal.st.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.FiscalDirectionResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;
import com.kojstarinnovations.terminal.st.domain.model.FiscalDirectionDTO;

public interface FiscalDirectionOP extends OutputPort<FiscalDirectionDTO, FiscalDirectionResponse, String> {

}