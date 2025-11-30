package com.kojstarinnovations.terminal.commons.data.payload.payment;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * CreditCardDetailResponse - Payload for Credit Card Detail with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCardDetailResponse extends AuditAttributeResponse {
    private Integer id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
}