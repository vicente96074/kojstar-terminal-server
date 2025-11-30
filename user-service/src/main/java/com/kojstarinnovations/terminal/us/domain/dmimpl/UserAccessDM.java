package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserAccessResponse;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.application.data.request.UserAccessRequest;
import com.kojstarinnovations.terminal.us.domain.model.UserAccessDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * UserAccessDM - Domain Mapper for UserAccess transport, dto and payload
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserAccessDM implements DomainMapper<UserAccessDTO, UserAccessRequest, UserAccessResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public UserAccessDTO requestToDTO(UserAccessRequest request) {
        return modelMapper.map(request, UserAccessDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public UserAccessResponse dtoToResponse(UserAccessDTO dto) {
        return modelMapper.map(dto, UserAccessResponse.class);
    }

    private final ModelMapperCustomized modelMapper;
}