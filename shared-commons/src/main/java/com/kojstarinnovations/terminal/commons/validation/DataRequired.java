package com.kojstarinnovations.terminal.commons.validation;

import com.kojstarinnovations.terminal.commons.validators.DataRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * DataRequired - Annotation to validate if a field is required
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Documented
@Constraint(validatedBy = DataRequiredValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataRequired {

    /**
     * Message to be displayed when the validation fails
     *
     * @return the message
     */
    String message() default "This field is required";

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