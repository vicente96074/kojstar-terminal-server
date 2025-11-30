package com.kojstarinnovations.terminal.commons.data.payload.userservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kojstarinnovations.terminal.commons.data.payload.commons.BasicAuditResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * UserResponse - Payload for User with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BasicAuditResponse {
    private String id;
    private String transactionId;
    private String firstName;
    private String middleName;
    private String firstSurname;
    private String secondSurname;
    private String marriageSurname;
    private String username;
    private String email;
    private LocalDate birthDate;
    private String storeBranchId;
    private String storeId;

    private String userSettingId;

    private Set<RolResponse> rolResponses;
    private Set<AccessResponse> accessResponses;
    private List<IdentificationUSResponse> identificationUSResponses;
    private List<ContactUSResponse> contactUSResponses;
    private List<AddressUSResponse> addressUSResponses;
}