package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.payload.storeservice.BranchOfficeResponse;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.st.domain.model.BranchOfficeDTO;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.entity.BranchOffice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BranchOfficePM implements PersistenceMapper<BranchOffice, BranchOfficeDTO, BranchOfficeResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public BranchOffice dtoToEntity(BranchOfficeDTO dto) {
        return mapper.map(dto, BranchOffice.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public BranchOfficeDTO entityToDTO(BranchOffice entity) {
        return mapper.map(entity, BranchOfficeDTO.class);
    }

    /**
     * Maps an entity to response
     *
     * @param entity persisted
     * @return payload
     */
    @Override
    public BranchOfficeResponse entityToResponse(BranchOffice entity) {
        return mapper.map(entity, BranchOfficeResponse.class);
    }

    private final ModelMapperCustomized mapper;
}