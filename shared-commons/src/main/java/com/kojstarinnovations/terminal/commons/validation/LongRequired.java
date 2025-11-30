package com.kojstarinnovations.terminal.commons.validation;

import com.kojstarinnovations.terminal.commons.validators.LongRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * LongRequired - Annotation to validate if a field with a long value is required
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LongRequiredValidator.class)
public @interface LongRequired {

    /**
     * Message to be displayed when the validation fails
     *
     * @return the message
     */
    String message() default "This field must be a long";

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