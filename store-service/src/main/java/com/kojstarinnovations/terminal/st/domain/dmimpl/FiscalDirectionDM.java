package com.kojstarinnovations.terminal.st.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.FiscalDirectionResponse;
import com.kojstarinnovations.terminal.commons.data.transport.storeservice.FiscalDirectionRequest;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.st.domain.model.FiscalDirectionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FiscalDirectionDM implements DomainMapper<FiscalDirectionDTO, FiscalDirectionRequest, FiscalDirectionResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public FiscalDirectionDTO requestToDTO(FiscalDirectionRequest request) {
        return mapper.map(request, FiscalDirectionDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public FiscalDirectionResponse dtoToResponse(FiscalDirectionDTO dto) {
        return mapper.map(dto, FiscalDirectionResponse.class);
    }

    private final ModelMapperCustomized mapper;
}
