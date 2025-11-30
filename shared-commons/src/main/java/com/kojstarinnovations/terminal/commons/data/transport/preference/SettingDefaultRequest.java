package com.kojstarinnovations.terminal.commons.data.transport.preference;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuthPayload;
import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SettingDefaultRequest extends CommonsRequest {
    private String userId;
    private String urlProfilePicture;
    private AuthPayload authPayload;
}