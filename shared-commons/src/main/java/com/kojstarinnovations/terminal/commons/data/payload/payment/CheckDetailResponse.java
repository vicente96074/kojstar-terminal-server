package com.kojstarinnovations.terminal.commons.data.payload.payment;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * CheckDetailResponse - Payload for Check Detail with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckDetailResponse extends AuditAttributeResponse {
    private Integer id;
    private String bank;
    private String checkNumber;
    private LocalDate checkDate;
}