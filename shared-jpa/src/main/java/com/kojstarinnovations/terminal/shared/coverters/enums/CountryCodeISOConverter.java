package com.kojstarinnovations.terminal.shared.coverters.enums;

import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CountryCodeISOConverter implements AttributeConverter<CountryCodeISO, String> {

    @Override
    public String convertToDatabaseColumn(CountryCodeISO attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public CountryCodeISO convertToEntityAttribute(String dbData) {
        return CountryCodeISO.fromCode(dbData)
                .orElse(null);
    }
}