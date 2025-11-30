package com.kojstarinnovations.terminal.commons.validation;

import com.kojstarinnovations.terminal.commons.validators.LocalDateRequiredValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

/**
 * LocalDateRequired - Annotation to validate if a field with a local date is required
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Documented
@Constraint(validatedBy = LocalDateRequiredValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateRequired {

    /**
     * Message to be displayed when the validation fails
     *
     * @return the message
     */
    String message() default "Fecha requerida";

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
    Class<?>[] payload() default {};
}