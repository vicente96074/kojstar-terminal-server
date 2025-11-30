package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.IntegerRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * IntegerRequiredValidator - Validator for the IntegerRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class IntegerRequiredValidator implements ConstraintValidator<IntegerRequired, Object> {

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
            Integer.parseInt(value.toString());
            return Integer.parseInt(value.toString()) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}