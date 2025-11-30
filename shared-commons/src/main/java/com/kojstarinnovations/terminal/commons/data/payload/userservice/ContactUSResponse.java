package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.commons.ContactResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactUSResponse extends ContactResponse {
    private String id;
    private String userId;
    private Status status;
    private UserResponse userResponse;
}