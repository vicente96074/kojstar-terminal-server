package com.kojstarinnovations.terminal.oauth2.infrastructure.output.mapper;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GoogleUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GoogleUserDTO;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GoogleUser;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleUserPM implements PersistenceMapper<GoogleUser, GoogleUserDTO, GoogleUserResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public GoogleUser dtoToEntity(GoogleUserDTO dto) {
        return modelMapper.map(dto, GoogleUser.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public GoogleUserDTO entityToDTO(GoogleUser entity) {
        return modelMapper.map(entity, GoogleUserDTO.class);
    }

    /**
     * Maps an entity to response
     *
     * @param entity persisted
     * @return payload
     */
    @Override
    public GoogleUserResponse entityToResponse(GoogleUser entity) {
        return modelMapper.map(entity, GoogleUserResponse.class);
    }

    /**
     * Maps an entity object to a dto object with authorities
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object with authorities
     */
    public GoogleUserResponse entityToResponseWithAuthorities(GoogleUser entity) {
        GoogleUserResponse response = modelMapper.map(entity, GoogleUserResponse.class);

        if (entity.getGoogleUserAccesses() != null && !entity.getGoogleUserAccesses().isEmpty()) {
            response.setAccessResponses(
                    entity.getGoogleUserAccesses().stream()
                            .filter(gua -> gua.getStatus() == Status.ACTIVE && gua.getAccess() != null)
                            .map(gua -> modelMapper.map(gua.getAccess(), AccessResponse.class))
                            .toList()
            );
        }

        if (entity.getGoogleUserRoles() != null && !entity.getGoogleUserRoles().isEmpty()) {
            response.setRolResponses(
                    entity.getGoogleUserRoles().stream()
                            .filter(gur -> gur.getStatus() == Status.ACTIVE && gur.getRol() != null)
                            .map(gur -> modelMapper.map(gur.getRol(), RolResponse.class))
                            .toList()
            );
        }

        return response;
    }

    private final ModelMapperCustomized modelMapper;
}