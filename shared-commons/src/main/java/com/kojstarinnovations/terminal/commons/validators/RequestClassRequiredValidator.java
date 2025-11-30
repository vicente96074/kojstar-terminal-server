package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.RequestClassRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * RequestClassRequiredValidator - Validator for the RequestClassRequired annotation
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
public class RequestClassRequiredValidator implements ConstraintValidator<RequestClassRequired, Object> {

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null;
    }
}