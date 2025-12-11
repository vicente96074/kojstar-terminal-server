package com.kojstarinnovations.terminal.oauth2.domain.dto;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserDTO {
    private String id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;

    private String storeId;
    private String clientVersion;
    private String deviceInfo;

    private Status status;

    private List<RolDTO> rolDTOs;
    private List<AccessDTO> accessDTOs;
}