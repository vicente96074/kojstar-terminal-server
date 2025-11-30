package com.kojstarinnovations.terminal.commons.data.payload.payment;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import com.kojstarinnovations.terminal.commons.data.enums.ModuleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * CashResponse - Payload for Cash with Audit Attributes,
 * module name is used to identify the module that is using the cash and is an enum value
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CashResponse extends AuditAttributeResponse {
    private Long id;
    private Integer cant1CentM;
    private Integer cant5CentM;
    private Integer cant10CentM;
    private Integer cant25CentM;
    private Integer cant50CentM;
    private Integer cant50CentB;
    private Integer cant1QuetzM;
    private Integer cant1QuetzB;
    private Integer cant5QuetzB;
    private Integer cant10QuetzB;
    private Integer cant20QuetzB;
    private Integer cant50QuetzB;
    private Integer cant100QuetzB;
    private Integer cant200QuetzB;
    private ModuleName moduleName;
}