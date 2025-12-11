package com.kojstarinnovations.terminal.shared.coverters.enums;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.enums.iso.IssuingAuthority;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IssuingAuthorityConverter implements AttributeConverter<IssuingAuthority, String> {

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database
     * column
     */
    @Override
    public String convertToDatabaseColumn(IssuingAuthority attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct <code>dbData</code> type for the corresponding
     * column for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param dbData the data from the database column to be
     *               converted
     * @return the converted value to be stored in the entity
     * attribute
     */
    @Override
    public IssuingAuthority convertToEntityAttribute(String dbData) {
        return IssuingAuthority.fromCode(dbData)
                .orElseThrow(() -> new NotFoundException(I18nCommonConstants.EXCEPTION_ISSUING_AUTHORITY_CODE_NOT_FOUND));
    }
}