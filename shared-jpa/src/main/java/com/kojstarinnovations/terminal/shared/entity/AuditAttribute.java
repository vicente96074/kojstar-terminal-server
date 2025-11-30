package com.kojstarinnovations.terminal.shared.entity;

import com.kojstarinnovations.terminal.commons.data.enums.ElementStatus;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AuditAttribute - This entity represents the persistence of the AuditAttribute in the database,
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class AuditAttribute {

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    //Attributes for audit purposes
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "element_status", columnDefinition = "ENUM('MIGRATED', 'NEW', 'UPDATED', 'DELETED') DEFAULT 'NEW'")
    @Enumerated(EnumType.STRING)
    private ElementStatus elementStatus;

    @Column(name = "status", columnDefinition = "ENUM('ACTIVE', 'INACTIVE', 'VALID', 'EXPIRED', 'APPROVED', 'REJECTED') DEFAULT 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;
}