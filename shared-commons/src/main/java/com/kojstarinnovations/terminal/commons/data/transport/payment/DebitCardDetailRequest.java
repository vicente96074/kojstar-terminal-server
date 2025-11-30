package com.kojstarinnovations.terminal.commons.data.transport.payment;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DebitCardDetailRequest - Transport for Debit Card Detail with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DebitCardDetailRequest extends CommonsRequest {

    @DataRequired(message = "Card number is required")
    private String cardNumber;

    @DataRequired(message = "Card holder name is required")
    private String cardHolderName;

    @DataRequired(message = "Expiration date is required")
    private String expirationDate;
}