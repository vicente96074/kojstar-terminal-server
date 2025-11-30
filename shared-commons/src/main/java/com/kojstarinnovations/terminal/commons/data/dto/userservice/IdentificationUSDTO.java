package com.kojstarinnovations.terminal.commons.data.dto.userservice;

import com.kojstarinnovations.terminal.commons.data.dto.commons.IdentificationDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.*;

/**
 * IdentificationUSDTO - Data Transfer Object for Identification with User
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IdentificationUSDTO extends IdentificationDTO {
    private String id;
    private String userId;
    private Status status;
    private UserDTO userDTO;
}