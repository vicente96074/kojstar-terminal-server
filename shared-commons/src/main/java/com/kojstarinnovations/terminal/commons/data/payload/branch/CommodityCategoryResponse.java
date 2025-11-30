package com.kojstarinnovations.terminal.commons.data.payload.branch;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CommodityCategoryResponse - Payload for Commodity Category with Audit Attributes,
 * Also contains a list of Commodity Responses for the category
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommodityCategoryResponse extends AuditAttributeResponse {
    private Integer id;
    private String name;
    private String description;
    private String filename;

    private List<CommodityResponse> commodityResponses;
}