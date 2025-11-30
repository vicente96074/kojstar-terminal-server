package com.kojstarinnovations.terminal.commons.validators;

import com.kojstarinnovations.terminal.commons.validation.ListOfRequestClassRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListOfRequestClassValidator implements ConstraintValidator<ListOfRequestClassRequired, List<?>> {

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        // Si la lista es null o vacía, no es válida
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Ahora verificamos que NINGUNO de los elementos sea null
        for (Object element : value) {
            if (element == null) {
                return false;
            }
        }

        return true; // Todo bien si la lista tiene elementos no nulos
    }
}