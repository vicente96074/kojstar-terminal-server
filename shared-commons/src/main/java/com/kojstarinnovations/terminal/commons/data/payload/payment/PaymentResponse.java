package com.kojstarinnovations.terminal.commons.data.payload.payment;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import com.kojstarinnovations.terminal.commons.data.enums.PaymentMethod;
import com.kojstarinnovations.terminal.commons.data.enums.PaymentType;
import com.kojstarinnovations.terminal.commons.data.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PaymentResponse - Payload for Payment with Audit Attributes,
 * And contains the next enums: PaymentMethod, PaymentType, TransactionType,
 * also contains the next related entities: CashResponse, CreditCardDetailResponse, DebitCardDetailResponse, CheckDetailResponse, TransferDetailResponse
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponse extends AuditAttributeResponse {
    private Long id;

    private Long cashId;
    private Integer creditCardDetailId;
    private Integer debitCardDetailId;
    private Integer checkDetailId;
    private Integer transferDetailId;

    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private TransactionType transactionType;
    private LocalDateTime date;
    private BigDecimal total;
    private BigDecimal totalCash;
    private BigDecimal totalCreditCard;
    private BigDecimal totalDebitCard;
    private BigDecimal totalCheck;
    private BigDecimal totalTransfer;

    private CashResponse cashResponse;
    private CreditCardDetailResponse creditCardDetailResponse;
    private DebitCardDetailResponse debitCardDetailResponse;
    private CheckDetailResponse checkDetailResponse;
    private TransferDetailResponse transferDetailResponse;
}