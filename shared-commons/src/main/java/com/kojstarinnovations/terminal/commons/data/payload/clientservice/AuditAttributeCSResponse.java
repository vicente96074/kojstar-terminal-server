package com.kojstarinnovations.terminal.commons.data.payload.clientservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AuditAttributeCSResponse - Payload for Audit Attribute with Client Service Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditAttributeCSResponse extends AuditAttributeResponse {
    private String storeId;
    private String storeBranchId;
    private String userId;
}