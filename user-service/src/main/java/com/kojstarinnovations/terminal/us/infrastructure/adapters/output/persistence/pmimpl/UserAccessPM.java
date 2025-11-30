package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.us.domain.model.UserAccessDTO;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * UserAccessPM - Persistence Mapper for UserAccess entity and dto
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserAccessPM implements PersistenceMapper<UserAccess, UserAccessDTO> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public UserAccess dtoToEntity(UserAccessDTO dto) {
        return modelMapper.map(dto, UserAccess.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public UserAccessDTO entityToDTO(UserAccess entity) {
        return modelMapper.map(entity, UserAccessDTO.class);
    }

    private final ModelMapperCustomized modelMapper;
}