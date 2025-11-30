package com.kojstarinnovations.terminal.commons.data.transport.cashier;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.data.transport.payment.CashRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.DecimalRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CashierRequest - Transport object for Cashier with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CashierRequest extends CommonsRequest {

    @DataRequired(message = "El id de la sucursal es requerido")
    private String storeBranchId;
    @DecimalRequired(message = "Opening balance is required")
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private BigDecimal totalSales;
    private BigDecimal totalPurchases;
    private BigDecimal totalExpenses;
    private BigDecimal availableBalance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closingTime;
    private CashRequest openingCashRequest;
    private CashRequest closingCashRequest;
}
