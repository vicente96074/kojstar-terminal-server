package com.kojstarinnovations.terminal.st.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.BranchOfficeResponse;
import com.kojstarinnovations.terminal.commons.data.transport.storeservice.BranchOfficeRequest;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.st.domain.model.BranchOfficeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BranchOfficeDM implements DomainMapper<BranchOfficeDTO, BranchOfficeRequest, BranchOfficeResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public BranchOfficeDTO requestToDTO(BranchOfficeRequest request) {
        return mapper.map(request, BranchOfficeDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public BranchOfficeResponse dtoToResponse(BranchOfficeDTO dto) {
        return mapper.map(dto, BranchOfficeResponse.class);
    }

    private final ModelMapperCustomized mapper;
}