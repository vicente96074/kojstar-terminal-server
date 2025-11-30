package com.kojstarinnovations.terminal.commons.data.payload.branch;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.*;

import java.math.BigDecimal;

/**
 * StockResponse - Payload for Stock with Audit Attributes,
 * Also contains Commodity and Store payloads
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockResponse extends AuditAttributeResponse {
    private Integer id;
    private Integer commodityId;
    private String storeBranchId;

    private BigDecimal priceSale;
    private BigDecimal priceWholesale;
    private Integer stock;
    private BigDecimal percentDiscount;

    private CommodityResponse commodityResponse;
    private StoreResponse storeResponse;
}