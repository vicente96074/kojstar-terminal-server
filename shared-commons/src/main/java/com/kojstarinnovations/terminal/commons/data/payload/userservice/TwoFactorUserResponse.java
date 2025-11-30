package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.TwoFactorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TwoFactorUserResponse extends TwoFactorResponse {
    private String id;
    private String userId;
    private UserResponse userResponse;
}