package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.BasicAuditResponse;
import lombok.*;

/**
 * AuditAttributeUSResponse - Payload for Audit Attribute with User Credentials
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditAttributeUSResponse extends BasicAuditResponse {
    private String userCredentials;
}