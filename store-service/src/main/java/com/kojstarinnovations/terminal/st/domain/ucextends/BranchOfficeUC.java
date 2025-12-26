package com.kojstarinnovations.terminal.st.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.BranchOfficeResponse;
import com.kojstarinnovations.terminal.commons.data.transport.storeservice.BranchOfficeRequest;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;

public interface BranchOfficeUC extends UseCase<BranchOfficeRequest, BranchOfficeResponse, String> {
}