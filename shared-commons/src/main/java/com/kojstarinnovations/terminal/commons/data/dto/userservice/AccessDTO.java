package com.kojstarinnovations.terminal.commons.data.dto.userservice;

import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AccessDTO - Data Transfer Object for Access
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessDTO  {
    private String id;
    private AccessName accessName;
    private Status status;
}