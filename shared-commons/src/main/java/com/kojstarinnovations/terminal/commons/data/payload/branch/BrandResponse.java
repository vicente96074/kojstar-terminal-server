package com.kojstarinnovations.terminal.commons.data.payload.branch;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BrandResponse - Payload for Brand with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandResponse extends AuditAttributeResponse {
    private Integer id;
    private String name;
    private String acronym;
    private String description;
    private String filename;

    private List<CommodityResponse> commodityResponses;
}