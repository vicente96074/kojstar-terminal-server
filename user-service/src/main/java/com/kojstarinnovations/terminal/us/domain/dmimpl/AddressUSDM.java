package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.AddressUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.SubdivisionRegionCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AddressUSResponse;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.application.data.request.AddressUSRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * AddressUSDM - Domain Mapper for AddressUS transport, dto and payload
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class AddressUSDM implements DomainMapper<AddressUSDTO, AddressUSRequest, AddressUSResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public AddressUSDTO requestToDTO(AddressUSRequest request) {
        AddressUSDTO dto = modelMapper.map(request, AddressUSDTO.class);
        CountryCodeISO.fromCode(request.getCountryCode())
                .ifPresentOrElse(dto::setCountryCodeISO, () -> {
                    throw new NotFoundException(ExceptionConstants.COUNTRY_CODE_NOT_FOUND);
                });

        RegionCodeISO.fromCode(request.getRegionCode())
                .ifPresentOrElse(dto::setRegionCodeISO, () -> {
                    throw new NotFoundException(ExceptionConstants.REGION_CODE_NOT_FOUND);
                });

        SubdivisionRegionCodeISO.fromCode(request.getSubdivisionRegionCode())
                .ifPresentOrElse(dto::setSubdivisionRegionCodeISO, () -> {
                    throw new NotFoundException(ExceptionConstants.SUBDIVISION_REGION_CODE_NOT_FOUND);
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
    public AddressUSResponse dtoToResponse(AddressUSDTO dto) {
        AddressUSResponse response = modelMapper.map(dto, AddressUSResponse.class);
        response.setCountryCode(dto.getCountryCodeISO().getCode());
        response.setRegionCode(dto.getRegionCodeISO().getCode());
        response.setSubdivisionRegionCode(dto.getSubdivisionRegionCodeISO().getCode());

        return response;
    }

    private final ModelMapperCustomized modelMapper;
}