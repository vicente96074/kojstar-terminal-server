package com.kojstarinnovations.terminal.oauth2.infrastructure.output.mapper;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GithubUserResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GithubUserDTO;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GithubUser;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubUserPM implements PersistenceMapper<GithubUser, GithubUserDTO, GithubUserResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public GithubUser dtoToEntity(GithubUserDTO dto) {
        return modelMapper.map(dto, GithubUser.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public GithubUserDTO entityToDTO(GithubUser entity) {
        return modelMapper.map(entity, GithubUserDTO.class);
    }

    /**
     * Maps an entity to response
     *
     * @param entity persisted
     * @return payload
     */
    @Override
    public GithubUserResponse entityToResponse(GithubUser entity) {
        return modelMapper.map(entity, GithubUserResponse.class);
    }

    public GithubUserResponse entityToResponseWithAuthorities(GithubUser entity) {
        GithubUserResponse response = modelMapper.map(entity, GithubUserResponse.class);

        if (entity.getGithubUserAccesses() != null && !entity.getGithubUserAccesses().isEmpty()) {
            response.setAccessResponses(
                    entity.getGithubUserAccesses().stream()
                            .filter(gua -> gua.getStatus() == Status.ACTIVE && gua.getAccess() != null)
                            .map(gua -> modelMapper.map(gua.getAccess(), AccessResponse.class))
                            .toList()
            );
        }

        if (entity.getGithubUserRoles() != null && !entity.getGithubUserRoles().isEmpty()) {
            response.setRolResponses(
                    entity.getGithubUserRoles().stream()
                            .filter(gur -> gur.getStatus() == Status.ACTIVE && gur.getRol() != null)
                            .map(gur -> modelMapper.map(gur.getRol(), RolResponse.class))
                            .toList()
            );
        }

        return response;
    }

    private final ModelMapperCustomized modelMapper;
}