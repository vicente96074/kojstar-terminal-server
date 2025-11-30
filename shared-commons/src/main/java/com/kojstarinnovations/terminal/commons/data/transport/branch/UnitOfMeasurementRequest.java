package com.kojstarinnovations.terminal.commons.data.transport.branch;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * UnitOfMeasurementRequest - Transport object for Unit of Measurement with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UnitOfMeasurementRequest extends CommonsRequest {

    @DataRequired(message = "La presentación de la unidad de medida es requerida")
    private String presentation;

    @DataRequired(message = "Las siglas de la unidad de medida son requeridas")
    private String acronym;

    @DataRequired(message = "La descripción de la unidad de medida es requerida")
    private String description;
}