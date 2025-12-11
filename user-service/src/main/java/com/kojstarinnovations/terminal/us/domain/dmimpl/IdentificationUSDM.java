package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.constants.I18nUserConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.IdentificationUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.IdentificationUSResponse;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.application.data.request.IdentificationUSRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Identification - Domain Mapper for IdentificationUS transport, dto and payload
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class IdentificationUSDM implements DomainMapper<IdentificationUSDTO, IdentificationUSRequest, IdentificationUSResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public IdentificationUSDTO requestToDTO(IdentificationUSRequest request) {
        IdentificationUSDTO dto = modelMapper.map(request, IdentificationUSDTO.class);
        CountryCodeISO.fromCode(request.getNationalityCode())
                .ifPresentOrElse(dto::setNationality, () -> {
                    throw new NotFoundException(I18nUserConstants.EXCEPTION_IDENTIFICATION_COUNTRY_CODE_NOT_FOUND);
                });

        return dto;
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public IdentificationUSResponse dtoToResponse(IdentificationUSDTO dto) {
        IdentificationUSResponse response = modelMapper.map(dto, IdentificationUSResponse.class);
        response.setNationalityCode(dto.getNationality().getCode());
        return response;
    }

    private final ModelMapperCustomized modelMapper;
}