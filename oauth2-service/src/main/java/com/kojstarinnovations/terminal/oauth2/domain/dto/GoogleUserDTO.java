package com.kojstarinnovations.terminal.oauth2.domain.dto;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GoogleUserDTO extends BasicAuditDTO {
    private String id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;

    private String storeId;
    private String clientVersion;
    private String deviceInfo;

    private List<RolDTO> rolDTOs;
    private List<AccessDTO> accessDTOs;
}