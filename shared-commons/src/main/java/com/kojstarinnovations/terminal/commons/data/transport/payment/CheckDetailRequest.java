package com.kojstarinnovations.terminal.commons.data.transport.payment;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.LocalDateRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * CheckDetailRequest - Transport for Check Detail with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CheckDetailRequest extends CommonsRequest {

    @DataRequired(message = "El nombre del banco es requerido para el registro de cheque")
    private String bank;

    @DataRequired(message = "El número de cheque es requerido para el registro de cheque")
    private String checkNumber;

    @LocalDateRequired(message = "La fecha de cheque es requerida para el registro de cheque")
    private LocalDate checkDate;
}