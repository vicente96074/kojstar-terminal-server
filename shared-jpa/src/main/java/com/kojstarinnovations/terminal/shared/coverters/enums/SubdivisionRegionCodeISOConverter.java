package com.kojstarinnovations.terminal.shared.coverters.enums;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.enums.iso.SubdivisionRegionCodeISO;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SubdivisionRegionCodeISOConverter implements AttributeConverter<SubdivisionRegionCodeISO, String> {

    @Override
    public String convertToDatabaseColumn(SubdivisionRegionCodeISO attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public SubdivisionRegionCodeISO convertToEntityAttribute(String dbData) {
        return SubdivisionRegionCodeISO.fromCode(dbData)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.SUBDIVISION_REGION_CODE_NOT_FOUND));
    }
}