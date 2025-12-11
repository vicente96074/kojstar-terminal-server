package com.kojstarinnovations.terminal.commons.data.transport.userservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {
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
    private String phone;
    private String userSettingId;
    private List<String> rolesRequest;
    private List<String> accessesRequest;
}