package com.kojstarinnovations.terminal.commons.data.payload.storeservice;

import com.kojstarinnovations.terminal.commons.data.enums.BusinessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StoreResponse - Payload for Store with Audit Attributes,
 * Also contains Location, a list of Store Branches, and a list of Commodities payloads
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StoreResponse {
    private String id;
    private Integer locationId;
    private String ceo;

    private String nit;
    private String name;
    private String description;
    private String phone;
    private String email;
    private String filename;
    private BusinessType businessType;
    private String webSite;

    /*private FiscalDirectionResponse locationResponse;
    private List<StoreBranchResponse> storeBranchResponses;
    private List<CommodityResponse> commoditiesResponses;*/
}