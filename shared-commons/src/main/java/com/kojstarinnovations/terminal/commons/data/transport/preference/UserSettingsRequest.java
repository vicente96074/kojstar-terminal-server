package com.kojstarinnovations.terminal.commons.data.transport.preference;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSettingsRequest {
    private String userId;
    private String userSettingId;
    private Boolean ghSpanShowable;
    private String theme;
    private String language;
    private String urlProfilePicture;
}