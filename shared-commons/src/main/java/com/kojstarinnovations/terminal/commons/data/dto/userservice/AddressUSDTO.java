package com.kojstarinnovations.terminal.commons.data.dto.userservice;

import com.kojstarinnovations.terminal.commons.data.dto.commons.AddressDTO;
import lombok.*;

/**
 * AddressUSDTO - Data Transfer Object for User Service Address
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressUSDTO extends AddressDTO {
    private String id;
    private String userId;
    private UserDTO userDTO;
}