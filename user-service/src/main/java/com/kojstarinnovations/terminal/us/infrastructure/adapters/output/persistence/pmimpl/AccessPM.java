package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * AccessPM - Persistence Mapper for Access entity and dto
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class AccessPM implements PersistenceMapper<Access, AccessDTO> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public Access dtoToEntity(AccessDTO dto) {
        return modelMapper.map(dto, Access.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public AccessDTO entityToDTO(Access entity) {
        return modelMapper.map(entity, AccessDTO.class);
    }

    private final ModelMapperCustomized modelMapper;
}