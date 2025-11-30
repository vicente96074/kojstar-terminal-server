package com.kojstarinnovations.terminal.us.application.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRolRequest - Transport object for user rol requests
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolRequest {
    private String userId;
    private String rolId;
}