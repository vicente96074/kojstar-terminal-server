package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.IdentificationUSDTO;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.IdentificationUS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * IdentificationUSPM - Persistence Mapper for IdentificationUS entity and dto
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class IdentificationUSPM implements PersistenceMapper<IdentificationUS, IdentificationUSDTO> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public IdentificationUS dtoToEntity(IdentificationUSDTO dto) {
        return modelMapper.map(dto, IdentificationUS.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public IdentificationUSDTO entityToDTO(IdentificationUS entity) {
        return modelMapper.map(entity, IdentificationUSDTO.class);
    }

    private final ModelMapperCustomized modelMapper;
}