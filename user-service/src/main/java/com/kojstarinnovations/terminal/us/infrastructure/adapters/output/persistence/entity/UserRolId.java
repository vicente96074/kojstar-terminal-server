package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserRolId - This class represents the composite key for the UserRol entity.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRolId implements Serializable {
    private String userId;
    private String rolId;
}