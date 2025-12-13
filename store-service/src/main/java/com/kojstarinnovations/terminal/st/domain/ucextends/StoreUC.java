package com.kojstarinnovations.terminal.st.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.commons.data.transport.storeservice.StoreRequest;

public interface StoreUC extends UseCase<StoreRequest, StoreResponse, String> {
}