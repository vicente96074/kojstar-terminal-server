package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.AddressUSDTO;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.AddressUS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * AddressUSPM - Persistence Mapper for AddressUS entity and dto
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class AddressUSPM implements PersistenceMapper<AddressUS, AddressUSDTO> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public AddressUS dtoToEntity(AddressUSDTO dto) {
        return modelMapper.map(dto, AddressUS.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public AddressUSDTO entityToDTO(AddressUS entity) {
        return modelMapper.map(entity, AddressUSDTO.class);
    }

    private final ModelMapperCustomized modelMapper;
}