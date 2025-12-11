package com.kojstarinnovations.terminal.commons.data.payload.branch;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * StoreBranchResponse - Payload for Store Branch with Audit Attributes,
 * Also contains a list of Commodity Responses for the store branch,
 * and the Store and Fiscal Direction payloads
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreBranchResponse extends AuditAttributeResponse {
    private String id;
    private String storeId;
    private Integer locationId;

    private String nit;
    private String name;
    private String phone;
    private String email;
    private String openingHours;
    private String closingHours;
    private String filename;
    private String generalManager;

    private FiscalDirectionResponse locationResponse;
    private StoreResponse storeResponse;
    private List<CommodityResponse> commodityResponses;
}