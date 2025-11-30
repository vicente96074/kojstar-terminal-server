package com.kojstarinnovations.terminal.commons.data.dto.userservice;

import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RolDTO - Data Transfer Object for Rol
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolDTO {
    private String id;
    private RolName rolName;
    private Status status;
}