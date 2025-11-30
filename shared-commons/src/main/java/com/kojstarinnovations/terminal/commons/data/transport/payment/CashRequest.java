package com.kojstarinnovations.terminal.commons.data.transport.payment;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * CashRequest - Transport for Cash with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CashRequest extends CommonsRequest {

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

    @DataRequired(message = "El nombre del módulo es requerido para el registro de caja")
    private String moduleName;
}