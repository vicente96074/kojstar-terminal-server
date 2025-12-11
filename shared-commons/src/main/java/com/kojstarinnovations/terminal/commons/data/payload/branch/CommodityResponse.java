package com.kojstarinnovations.terminal.commons.data.payload.branch;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CommodityResponse - Payload for Commodity with Audit Attributes,
 * Also contains a list of Store Branch Responses for the commodity,
 * and the Store, Brand, Unit of Measurement and Commodity Category payloads
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommodityResponse extends AuditAttributeResponse {
    private Integer id;
    private String storeId;
    private Integer unitOfMeasurementId;
    private Integer categoryId;
    private Integer brandId;
    private String barcode;

    private String name;
    private String description;
    private String activePrinciple;
    private String filename;

    private StoreResponse storeResponse;
    private BrandResponse brandResponse;
    private UnitOfMeasurementResponse unitOfMeasurementResponse;
    private CommodityCategoryResponse commodityCategoryResponse;

    private List<StoreBranchResponse> storeBranchResponses;
}