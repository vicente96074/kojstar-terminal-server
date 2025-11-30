package com.kojstarinnovations.terminal.us.domain.model;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * UserRolDTO - Data Transfer Object for UserRol
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRolDTO extends BasicAuditDTO {
    private String userId;
    private String rolId;
}