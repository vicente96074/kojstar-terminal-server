package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.IdentificationUSResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.application.data.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * UserDM - Domain Mapper for User transport, dto and payload
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserDM implements DomainMapper<UserDTO, UserRequest, UserResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public UserDTO requestToDTO(UserRequest request) {
        return modelMapper.map(request, UserDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public UserResponse dtoToResponse(UserDTO dto) {
        UserResponse response = modelMapper.map(dto, UserResponse.class);

        if (dto.getRolDTOs() != null && !dto.getRolDTOs().isEmpty()) {
            response.setRolResponses(
                    dto.getRolDTOs().stream()
                            .map(rolDTO -> modelMapper.map(rolDTO, RolResponse.class))
                            .collect(Collectors.toSet())
            );
        }

        if (dto.getAccessDTOs() != null && !dto.getAccessDTOs().isEmpty()) {
            response.setAccessResponses(
                    dto.getAccessDTOs().stream()
                            .map(accessDTO -> modelMapper.map(accessDTO, AccessResponse.class))
                            .collect(Collectors.toSet())
            );
        }

        if (dto.getIdentificationUSDTOs() != null && !dto.getIdentificationUSDTOs().isEmpty()) {
            response.setIdentificationUSResponses(
                    dto.getIdentificationUSDTOs().stream()
                            .map(idUSDTO -> modelMapper.map(idUSDTO, IdentificationUSResponse.class))
                            .collect(Collectors.toList())
            );
        }

        return response;
    }

    private final ModelMapperCustomized modelMapper;
}