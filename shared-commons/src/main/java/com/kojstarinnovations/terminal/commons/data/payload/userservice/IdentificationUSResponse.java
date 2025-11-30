package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.commons.IdentificationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * IdentificationUSResponse - Payload for Identification with User Response,
 * the user is a payload that represents the User of the Identification
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdentificationUSResponse extends IdentificationResponse {
    private String id;
    private String userId;
    private Status status;
    private UserResponse userResponse;
}