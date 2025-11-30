package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * UserRolResponse - Payload for User Rol with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRolResponse extends AuditAttributeUSResponse {
    private String userId;
    private String rolId;
}