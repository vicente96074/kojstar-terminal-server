package com.kojstarinnovations.terminal.commons.data.payload.cashier;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import com.kojstarinnovations.terminal.commons.data.payload.payment.CashResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CashierResponse - Payload for Cashier with Audit Attributes,
 * Also contains Cash payloads for opening and closing cash
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CashierResponse extends AuditAttributeResponse {
    private Integer id;
    private String storeBranchId;
    private Long openingCashId;
    private Long closingCashId;

    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private BigDecimal totalSales;
    private BigDecimal totalPurchases;
    private BigDecimal totalExpenses;
    private BigDecimal availableBalance;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private CashResponse openingCashResponse;
    private CashResponse closingCashResponse;
}