package com.kojstarinnovations.terminal.commons.data.transport.payment;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * TransferDetailRequest - Transport for Transfer Detail with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TransferDetailRequest extends CommonsRequest {

    @DataRequired(message = "Account number is required")
    private String accountNumber;

    @DataRequired(message = "Account holder name is required")
    private String accountHolderName;

    @DataRequired(message = "Bank name is required")
    private String bankName;
}