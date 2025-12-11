package com.kojstarinnovations.terminal.st.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.st.application.data.request.StoreRequest;

public interface StoreUC extends UseCase<StoreRequest, StoreResponse, String> {
    StoreResponse execute(StoreRequest request);
}