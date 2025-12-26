package com.kojstarinnovations.terminal.commons.data.payload.storeservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficeResponse {
    private String id;
    private String storeId;
    private Integer locationId;
    private String nit;
    private String name;
    private String phone;
    private String email;
    private String openingHours;
    private String closingHours;
    private String logo;
    private String generalManager;
    private FiscalDirectionResponse fiscalDirectionResponse;
    private StoreResponse storeResponse;
}
