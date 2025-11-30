package com.kojstarinnovations.terminal.commons.data.dto.userservice;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * UserDTO - Data Transfer Object for User
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO extends BasicAuditDTO {
    private String id;
    private String transactionId;
    private String firstName;
    private String middleName;
    private String firstSurname;
    private String secondSurname;
    private String marriageSurname;
    private String username;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String storeBranchId;
    private String storeId;

    private String userSettingId;

    private List<RolDTO> rolDTOs;
    private List<AccessDTO> accessDTOs;
    private List<IdentificationUSDTO> identificationUSDTOs;
    private List<ContactUSDTO> contactUSDTOs;
    private List<AddressUSDTO> addressUSDTOs;
    private List<TwoFactorUserDTO> twoFactorUserDTOs;
    private Boolean hasAnyTwoFactorActive;
}