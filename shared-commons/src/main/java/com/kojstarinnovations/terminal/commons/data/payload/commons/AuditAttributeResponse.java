package com.kojstarinnovations.terminal.commons.data.payload.commons;

import com.kojstarinnovations.terminal.commons.data.enums.ElementStatus;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * AuditAttributeResponse - Payload for Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class AuditAttributeResponse {
    private String transactionId;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ElementStatus elementStatus;
    private TransactionStatus transactionStatus;
    private Status status;
}