package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.DecimalRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * DecimalRequiredValidator - Validator for the DecimalRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class DecimalRequiredValidator implements ConstraintValidator<DecimalRequired, Object> {

    /**
     * Method to validate if the value is a number
     *
     * @param value   the value to be validated
     * @param context the context
     * @return true if the value is a number
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        //Try to parse the value to a BigDecimal, if it fails, it means that the value is not a number
        try {
            new BigDecimal(value.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}