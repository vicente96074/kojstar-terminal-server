package com.kojstarinnovations.terminal.commons.data.transport.userservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuthPayload;
import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
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
public class NewUserSettingRequest extends CommonsRequest {
    private String userId;
    private String userSettingId;
    private AuthPayload authPayload;
}