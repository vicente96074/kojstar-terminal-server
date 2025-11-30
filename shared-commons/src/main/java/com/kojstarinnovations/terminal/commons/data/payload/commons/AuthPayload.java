package com.kojstarinnovations.terminal.commons.data.payload.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthPayload {
    private String userId;
    private String storeBranchId;
    private String storeId;
}