package com.kojstarinnovations.terminal.shared.coverters.enums;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RegionCodeISOConverter implements AttributeConverter<RegionCodeISO, String> {

    @Override
    public String convertToDatabaseColumn(RegionCodeISO attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public RegionCodeISO convertToEntityAttribute(String dbData) {
        return RegionCodeISO.fromCode(dbData)
                .orElseThrow(() -> new NotFoundException(I18nCommonConstants.EXCEPTION_REGION_CODE_NOT_FOUND));
    }
}