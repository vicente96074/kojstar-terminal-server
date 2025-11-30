package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.LocalDateRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * LocalDateRequiredValidator - Validator for the LocalDateRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class LocalDateRequiredValidator implements ConstraintValidator<LocalDateRequired, Object> {

    /**
     * Method to validate if the value is a LocalDate
     *
     * @param value   the value to be validated
     * @param context the context
     * @return true if the value is a LocalDate
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            LocalDate date = (LocalDate) value;
            return date != null;
        } catch (Exception e) {
            return false;
        }
    }
}