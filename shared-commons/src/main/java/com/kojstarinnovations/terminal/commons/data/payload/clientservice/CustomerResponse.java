package com.kojstarinnovations.terminal.commons.data.payload.clientservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * CustomerResponse - Payload for Customer with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerResponse extends AuditAttributeResponse {
    private Integer id;
    private Integer personId;
    private BigDecimal recordConsumption;
}