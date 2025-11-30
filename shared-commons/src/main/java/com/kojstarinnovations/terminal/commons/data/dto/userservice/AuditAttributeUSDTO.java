package com.kojstarinnovations.terminal.commons.data.dto.userservice;

import com.kojstarinnovations.terminal.commons.data.dto.commons.AuditAttributeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AuditAttributeUSDTO - Data Transfer Object for AuditAttribute with UserCredentials
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuditAttributeUSDTO extends AuditAttributeDTO {
    private String userCredentials;
}