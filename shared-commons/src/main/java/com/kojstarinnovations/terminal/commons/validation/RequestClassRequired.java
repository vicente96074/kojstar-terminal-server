package com.kojstarinnovations.terminal.commons.validation;

import com.kojstarinnovations.terminal.commons.validators.RequestClassRequiredValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

/**
 * RequestClassRequired - Annotation to validate if a transport class is required
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Documented
@Constraint(validatedBy = RequestClassRequiredValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestClassRequired {

    /**
     * Message to be displayed when the validation fails
     *
     * @return the message
     */
    String message() default "Request class is required";

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