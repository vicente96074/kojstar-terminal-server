package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserRolResponse;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.us.domain.model.UserRolDTO;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserRol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * UserRolPM - Persistence Mapper for UserRol entity and dto
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserRolPM implements PersistenceMapper<UserRol, UserRolDTO, UserRolResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public UserRol dtoToEntity(UserRolDTO dto) {
        return modelMapper.map(dto, UserRol.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public UserRolDTO entityToDTO(UserRol entity) {
        return modelMapper.map(entity, UserRolDTO.class);
    }

    /**
     * Maps an entity to response
     *
     * @param userRol persisted
     * @return payload
     */
    @Override
    public UserRolResponse entityToResponse(UserRol userRol) {
        return modelMapper.map(userRol, UserRolResponse.class);
    }

    private final ModelMapperCustomized modelMapper;
}