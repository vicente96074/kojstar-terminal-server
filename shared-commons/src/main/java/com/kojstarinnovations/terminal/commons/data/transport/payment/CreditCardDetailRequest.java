package com.kojstarinnovations.terminal.commons.data.transport.payment;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * CreditCardDetailRequest - Transport for Credit Card Detail with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CreditCardDetailRequest extends CommonsRequest {

    @DataRequired(message = "El número de tarjeta es requerido para el registro de tarjeta de crédito")
    private String cardNumber;

    @DataRequired(message = "El nombre del titular de la tarjeta es requerido para el registro de tarjeta de crédito")
    private String cardHolderName;

    @DataRequired(message = "La fecha de expiración de la tarjeta es requerida para el registro de tarjeta de crédito")
    private String expirationDate;
}