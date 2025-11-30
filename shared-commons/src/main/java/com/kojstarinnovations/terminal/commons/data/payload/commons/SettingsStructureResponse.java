package com.kojstarinnovations.terminal.commons.data.payload.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SettingsStructureResponse - Payload for Settings Structure
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsStructureResponse {
    private String id;
    private String userId;
    private boolean ghSpanShowable;
    private String theme;
    private String language;
    private String urlProfilePicture;
}