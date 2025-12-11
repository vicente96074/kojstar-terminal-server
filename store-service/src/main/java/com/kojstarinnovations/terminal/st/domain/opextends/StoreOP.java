package com.kojstarinnovations.terminal.st.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;
import com.kojstarinnovations.terminal.st.domain.model.StoreDTO;

public interface StoreOP extends OutputPort<StoreDTO, StoreResponse, String> {
}