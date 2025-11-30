package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AddressUSResponse - Payload for Address with User Response,
 * the user is a payload that represents the User of the Address
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressUSResponse extends AddressResponse {
    private String id;
    private String userId;
    private UserResponse userResponse;
}