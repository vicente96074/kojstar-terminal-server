package com.kojstarinnovations.terminal.commons.data.payload.storeservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.BasicAuditResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * FiscalDirectionResponse - Payload for Fiscal Direction with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FiscalDirectionResponse extends BasicAuditResponse {
    private String id;
    private String abbrev;
    private String identifierNumber;
    private String fiscalDirection;
    private String street;
    private String city;
    private String state;
    private String country;
    private String description;
}