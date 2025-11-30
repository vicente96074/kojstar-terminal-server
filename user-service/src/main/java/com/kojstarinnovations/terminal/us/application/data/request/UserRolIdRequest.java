package com.kojstarinnovations.terminal.us.application.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRolIdRequest - Transport object for user rol id requests
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolIdRequest {
    private String userId;
    private String rolId;
}
