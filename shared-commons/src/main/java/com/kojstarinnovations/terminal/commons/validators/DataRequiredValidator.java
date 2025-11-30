package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * DataRequiredValidator - Validator for the DataRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class DataRequiredValidator implements ConstraintValidator<DataRequired, String> {

    /**
     * Method to validate if the value is not null or empty
     *
     * @param value   the value to be validated
     * @param context the context
     * @return true if the value is not null or empty
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty();
    }
}