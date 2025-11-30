package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserAccessId - This class represents the composite key for the UserAccess entity.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessId implements Serializable {
    private String userId;
    private String accessId;
}