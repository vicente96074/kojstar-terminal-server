package com.kojstarinnovations.terminal.commons.data.payload.branch;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * UnitOfMeasurementResponse - Payload for Unit of Measurement with Audit Attributes,
 * Also contains a list of Commodities payload for the unit of measurement
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnitOfMeasurementResponse extends AuditAttributeResponse {
    private Integer id;
    private String presentation;
    private String acronym;
    private String description;

    private List<CommodityResponse> commodityResponses;
}