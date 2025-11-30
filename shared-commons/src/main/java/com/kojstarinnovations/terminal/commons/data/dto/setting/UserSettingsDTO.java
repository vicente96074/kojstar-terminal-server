package com.kojstarinnovations.terminal.commons.data.dto.setting;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserSettingsDTO - Data Transfer Object for user settings.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSettingsDTO {
    private String id;
    private String transactionId;
    private String userId;
    private String userSettingId;
    private Boolean ghSpanShowable;
    private String theme;
    private String language;
    private String urlProfilePicture;
}