package com.kojstarinnovations.terminal.commons.data.transport.userservice;

import com.kojstarinnovations.terminal.commons.data.transport.commons.TwoFactorRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TwoFactorUserRequest extends TwoFactorRequest {
    private String userId;
}
