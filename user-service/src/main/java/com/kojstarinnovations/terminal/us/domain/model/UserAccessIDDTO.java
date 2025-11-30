package com.kojstarinnovations.terminal.us.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRolIDDTO - Data Transfer Object for UserRolID
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessIDDTO {
    private String userId;
    private String accessId;
}