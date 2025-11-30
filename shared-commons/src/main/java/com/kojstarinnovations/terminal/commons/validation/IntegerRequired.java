package com.kojstarinnovations.terminal.commons.validation;

import com.kojstarinnovations.terminal.commons.validators.IntegerRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * IntegerRequired - Annotation to validate if a field with an integer value is required
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegerRequiredValidator.class)
public @interface IntegerRequired {

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