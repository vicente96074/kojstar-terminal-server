package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.TwoFactorUserDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorUserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.TwoFactorUserRequest;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TwoFactorUserDM implements DomainMapper<TwoFactorUserDTO, TwoFactorUserRequest, TwoFactorUserResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public TwoFactorUserDTO requestToDTO(TwoFactorUserRequest request) {
        return modelMapper.map(request, TwoFactorUserDTO.class);
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public TwoFactorUserResponse dtoToResponse(TwoFactorUserDTO dto) {
        return modelMapper.map(dto, TwoFactorUserResponse.class);
    }

    private final ModelMapperCustomized modelMapper;
}
