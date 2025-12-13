package com.kojstarinnovations.terminal.st.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.FiscalDirectionResponse;
import com.kojstarinnovations.terminal.commons.data.transport.storeservice.FiscalDirectionRequest;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;

public interface FiscalDirectionUC extends UseCase<FiscalDirectionRequest, FiscalDirectionResponse, String> {
}
