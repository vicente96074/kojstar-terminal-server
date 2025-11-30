package com.kojstarinnovations.terminal.commons.data.payload.payment;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * TransferDetailResponse - Payload for Transfer Detail with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferDetailResponse extends AuditAttributeResponse {
    private Integer id;
    private String accountNumber;
    private String accountHolderName;
    private String bankName;
}