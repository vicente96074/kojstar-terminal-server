package com.kojstarinnovations.terminal.commons.data.payload.branch;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import com.kojstarinnovations.terminal.commons.data.enums.BusinessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * StoreResponse - Payload for Store with Audit Attributes,
 * Also contains Location, a list of Store Branches, and a list of Commodities payloads
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreResponse extends AuditAttributeResponse {
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

    private FiscalDirectionResponse locationResponse;
    private List<StoreBranchResponse> storeBranchResponses;
    private List<CommodityResponse> commoditiesResponses;
}