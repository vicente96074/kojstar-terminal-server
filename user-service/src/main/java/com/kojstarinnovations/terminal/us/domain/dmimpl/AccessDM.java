package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.AccessRequest;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * AccessDM - Domain Mapper for Access transport, dto and payload
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class AccessDM implements DomainMapper<AccessDTO, AccessRequest, AccessResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public AccessDTO requestToDTO(AccessRequest request) {
        return modelMapper.map(request, AccessDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public AccessResponse dtoToResponse(AccessDTO dto) {
        return modelMapper.map(dto, AccessResponse.class);
    }

    private final ModelMapperCustomized modelMapper;
}