package com.kojstarinnovations.terminal.commons.validation;

import com.kojstarinnovations.terminal.commons.validators.IdIntegerRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * IdIntegerRequired - Annotation to validate if a field with an id integer is required
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Documented
@Constraint(validatedBy = IdIntegerRequiredValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdIntegerRequired {

    /**
     * Message to be displayed when the validation fails
     *
     * @return the message
     */
    String message() default "This field must be an integer";

    /**
     * Groups for the validation
     *
     * @return the groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload for the validation
     *
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};
}