package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.ContactUSDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.commons.pm.PM;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.ContactUS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactUSPM implements PM<ContactUS, ContactUSDTO, ContactUSResponse> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public ContactUS dtoToEntity(ContactUSDTO dto) {
        return mapper.map(dto, ContactUS.class);
    }

    /**
     * Maps an entity object to a payload
     *
     * @param entity the entity to be mapped
     * @return the mapped payload object
     */
    @Override
    public ContactUSResponse entityToResponse(ContactUS entity) {
        ContactUSResponse response = mapper.map(entity, ContactUSResponse.class);
        response.setCountryCode(entity.getCountryCodeISO().getCode());
        response.setRegionCode(entity.getRegionCodeISO().getCode());
        return response;
    }

    private final ModelMapperCustomized mapper;
}