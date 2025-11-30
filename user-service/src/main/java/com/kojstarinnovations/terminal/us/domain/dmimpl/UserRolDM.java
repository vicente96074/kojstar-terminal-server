package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserRolResponse;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.application.data.request.UserRolRequest;
import com.kojstarinnovations.terminal.us.domain.model.UserRolDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * UserRolDM - Domain Mapper for UserRol transport, dto and payload
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserRolDM implements DomainMapper<UserRolDTO, UserRolRequest, UserRolResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public UserRolDTO requestToDTO(UserRolRequest request) {
        return modelMapper.map(request, UserRolDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public UserRolResponse dtoToResponse(UserRolDTO dto) {
        return modelMapper.map(dto, UserRolResponse.class);
    }

    private final ModelMapperCustomized modelMapper;
}