package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.SQLInjectionMalicious;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class SQLInjectionMaliciousValidator implements ConstraintValidator<SQLInjectionMalicious, String> {

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
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // NoSQL injection check is not required for null or empty values
        }

        // Check for SQL injection patterns
        return !value.matches(SQL_INJECTION_REGEX);
    }

    private static final String SQL_INJECTION_REGEX =
            "(?i).*(['\";=\\-\\-]|(\\b(OR|AND|SELECT|INSERT|DELETE|UPDATE|DROP|UNION)\\b)).*";

}