package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.IdIntegerRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * IdIntegerRequiredValidator - Validator for the IdIntegerRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class IdIntegerRequiredValidator implements ConstraintValidator<IdIntegerRequired, Object> {

    /**
     * Method to validate if the value is an integer
     *
     * @param value   the value to be validated
     * @param context the context
     * @return true if the value is an integer
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        //Try to parse the value to an Integer, if it fails, it means that the value is not an integer
        try {
            Integer.parseInt(value.toString());
            return Integer.parseInt(value.toString()) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}