package com.kojstarinnovations.terminal.us.domain.dmimpl;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.ContactUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.commons.dm.DomainMapper;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.us.application.data.request.ContactUSRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContactUSDM implements DomainMapper<ContactUSDTO, ContactUSRequest, ContactUSResponse> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    @Override
    public ContactUSDTO requestToDTO(ContactUSRequest request) {
        ContactUSDTO dto = modelMapper.map(request, ContactUSDTO.class);

        if (request.getContactType().equals(ContactType.PHONE.name())) {
            CountryCodeISO.fromCode(request.getCountryCode())
                    .ifPresentOrElse(dto::setCountryCodeISO, () -> {
                        throw new NotFoundException(ExceptionConstants.COUNTRY_CODE_NOT_FOUND);
                    });
            RegionCodeISO.fromCode(request.getRegionCode())
                    .ifPresentOrElse(dto::setRegionCodeISO, () -> {
                        throw new NotFoundException(ExceptionConstants.REGION_CODE_NOT_FOUND);
                    });
        }
        return dto;
    }

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    @Override
    public ContactUSResponse dtoToResponse(ContactUSDTO dto) {
        ContactUSResponse response = modelMapper.map(dto, ContactUSResponse.class);

        if (dto.getContactType() == ContactType.PHONE) {
            response.setCountryCode(dto.getCountryCodeISO().getCode());
            response.setRegionCode(dto.getRegionCodeISO().getCode());
        }

        return response;
    }

    private final ModelMapperCustomized modelMapper;
}