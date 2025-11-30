package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.*;

/**
 * AccessResponse - Payload for Access with Audit Attributes,
 * the AccessName is an Enum that represents the name of the Access
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccessResponse {
    private String id;
    private AccessName accessName;
    private Status status;
}