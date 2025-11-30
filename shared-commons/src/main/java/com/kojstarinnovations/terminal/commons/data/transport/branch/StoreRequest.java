package com.kojstarinnovations.terminal.commons.data.transport.branch;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.IdIntegerRequired;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * StoreRequest - Transport object for Store with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StoreRequest extends CommonsRequest {

    @IdIntegerRequired(message = "El id de la ubicación es requerido")
    private Integer locationId;

    private String ceo;

    @DataRequired(message = "El nit de la tienda es requerido")
    private String nit;

    @DataRequired(message = "El nombre de la tienda es requerido")
    private String name;

    @DataRequired(message = "La descripción es requerida para la tienda")
    private String description;

    @DataRequired(message = "El contacto es requerido para la tienda")
    private String phone;

    @DataRequired(message = "El correo electrónico de la tienda es requerido")
    private String email;

    private String filename;

    @DataRequired(message = "El tipo de negocio es requerido")
    private String businessType;

    private String webSite;
}