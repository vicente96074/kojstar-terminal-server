package com.kojstarinnovations.terminal.commons.data.dto.userservice;

import com.kojstarinnovations.terminal.commons.data.dto.commons.ContactDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class ContactUSDTO extends ContactDTO {
    private String id;
    private String userId;
    private Status status;
    private UserDTO userDTO;
}