package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.EnumRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * EnumRequiredValidator - Validator for the EnumRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class EnumRequiredValidator implements ConstraintValidator<EnumRequired, Object> {

    /**
     * Method to initialize the validator
     *
     * @param value                          the annotation
     * @param context the context
     * @return true if the value is not null or empty
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value instanceof Enum;
    }
}