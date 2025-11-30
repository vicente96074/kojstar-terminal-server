package com.kojstarinnovations.terminal.us.application.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserAccessRequest - Transport object for user access requests
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessRequest {
    private String userId;
    private String accessId;
}
