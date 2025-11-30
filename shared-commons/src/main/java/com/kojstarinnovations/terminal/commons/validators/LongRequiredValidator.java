package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.LongRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * LongRequiredValidator - Validator for the LongRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class LongRequiredValidator implements ConstraintValidator<LongRequired, Object> {

    /**
     * Method to validate if the value is a number
     *
     * @param value   the value to be validated
     * @param context the context
     * @return true if the value is a number
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Long.parseLong(value.toString());
            return Long.parseLong(value.toString()) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}