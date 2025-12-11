package com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserAccessId - This class represents the composite key for the UserAccess entity.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessId implements Serializable {
    private String userId;
    private String accessId;
}