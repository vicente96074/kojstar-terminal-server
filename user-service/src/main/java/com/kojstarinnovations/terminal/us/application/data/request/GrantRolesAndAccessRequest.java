package com.kojstarinnovations.terminal.us.application.data.request;

import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * GrantRolesAndAccessRequest - Transport object for granting roles and access requests
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantRolesAndAccessRequest {
    private String transactionId;
    private List<String> roles;
    private List<String> accesses;

    @DataRequired(message = "User id is required")
    private String userId;

    @DataRequired(message = "Store branch id is required")
    private String storeBranchId;

    @DataRequired(message = "Store id is required")
    private String storeId;
}