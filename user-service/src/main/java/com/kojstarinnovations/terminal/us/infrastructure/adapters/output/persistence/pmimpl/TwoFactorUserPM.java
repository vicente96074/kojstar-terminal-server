package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.TwoFactorUserDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorUserResponse;
import com.kojstarinnovations.terminal.commons.pm.PM;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.TwoFactorUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TwoFactorUserPM implements PM<TwoFactorUser, TwoFactorUserDTO, TwoFactorUserResponse> {

    @Override
    public TwoFactorUser dtoToEntity(TwoFactorUserDTO dto) {
        return mapper.map(dto, TwoFactorUser.class);
    }

    @Override
    public TwoFactorUserResponse entityToResponse(TwoFactorUser entity) {
        return mapper.map(entity, TwoFactorUserResponse.class);
    }

    private final ModelMapperCustomized mapper;
}