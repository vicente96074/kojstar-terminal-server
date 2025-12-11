package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * RolPM - Persistence Mapper for Rol entity and dto
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class RolPM implements PersistenceMapper<Rol, RolDTO, RolResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public Rol dtoToEntity(RolDTO dto) {
        return modelMapper.map(dto, Rol.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public RolDTO entityToDTO(Rol entity) {
        return modelMapper.map(entity, RolDTO.class);
    }

    /**
     * Maps an entity to response
     *
     * @param entity persisted
     * @return payload
     */
    @Override
    public RolResponse entityToResponse(Rol entity) {
        return modelMapper.map(entity, RolResponse.class);
    }

    private final ModelMapperCustomized modelMapper;
}