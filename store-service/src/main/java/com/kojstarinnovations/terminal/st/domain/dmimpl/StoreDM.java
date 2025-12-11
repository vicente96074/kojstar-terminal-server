package com.kojstarinnovations.terminal.st.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.st.application.data.request.StoreRequest;
import com.kojstarinnovations.terminal.st.domain.model.StoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreDM implements DomainMapper<StoreDTO, StoreRequest, StoreResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public StoreDTO requestToDTO(StoreRequest request) {
        return mapper.map(request, StoreDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public StoreResponse dtoToResponse(StoreDTO dto) {
        return mapper.map(dto, StoreResponse.class);
    }

    private final ModelMapperCustomized mapper;
}