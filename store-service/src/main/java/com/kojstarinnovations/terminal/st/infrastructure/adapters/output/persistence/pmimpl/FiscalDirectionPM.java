package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.FiscalDirectionResponse;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.st.domain.model.FiscalDirectionDTO;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.entity.FiscalDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FiscalDirectionPM implements PersistenceMapper<FiscalDirection, FiscalDirectionDTO, FiscalDirectionResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public FiscalDirection dtoToEntity(FiscalDirectionDTO dto) {
        return mapper.map(dto, FiscalDirection.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public FiscalDirectionDTO entityToDTO(FiscalDirection entity) {
        return mapper.map(entity, FiscalDirectionDTO.class);
    }

    /**
     * Maps an entity to response
     *
     * @param entity persisted
     * @return payload
     */
    @Override
    public FiscalDirectionResponse entityToResponse(FiscalDirection entity) {
        return mapper.map(entity, FiscalDirectionResponse.class);
    }

    private final ModelMapperCustomized mapper;
}
