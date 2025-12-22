package com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.commons.BasicAuditResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponse extends BasicAuditResponse {
    private String id;
    private String transactionId;
    private String email;
    private String name;
    private String givenName;
    private String familyName;

    private String storeBranchId;
    private String storeId;
    private Boolean twoFactorEnabled;
    private Boolean twoFactorActive;

    private String clientVersion;
    private String deviceInfo;

    private Status status;

    private String userSettingId;

    private List<RolResponse> rolResponses;
    private List<AccessResponse> accessResponses;
}