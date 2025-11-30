package com.kojstarinnovations.terminal.us.domain.model;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * UserAccessDTO - Data Transfer Object for UserAccess
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserAccessDTO extends BasicAuditDTO {
    private String userId;
    private String accessId;
}