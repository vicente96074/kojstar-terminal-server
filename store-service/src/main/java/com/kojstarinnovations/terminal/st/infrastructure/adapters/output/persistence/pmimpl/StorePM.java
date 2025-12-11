package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.st.domain.model.StoreDTO;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorePM implements PersistenceMapper<Store, StoreDTO, StoreResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public Store dtoToEntity(StoreDTO dto) {
        return mapper.map(dto, Store.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public StoreDTO entityToDTO(Store entity) {
        return mapper.map(entity, StoreDTO.class);
    }

    /**
     * Maps an entity to response
     *
     * @param entity persisted
     * @return payload
     */
    @Override
    public StoreResponse entityToResponse(Store entity) {
        return mapper.map(entity, StoreResponse.class);
    }

    private final ModelMapperCustomized mapper;
}