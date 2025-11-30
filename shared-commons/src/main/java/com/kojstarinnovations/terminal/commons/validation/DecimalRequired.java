package com.kojstarinnovations.terminal.commons.validation;

import com.kojstarinnovations.terminal.commons.validators.DecimalRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * DecimalRequired - Annotation to validate if a field with a decimal value is required
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Documented
@Constraint(validatedBy = DecimalRequiredValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DecimalRequired {

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