package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RolResponse - Payload for Rol with Audit Attributes,
 * the RolName is an Enum that represents the name of the Rol
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolResponse {
    private String id;
    private RolName rolName;
    private Status status;
}