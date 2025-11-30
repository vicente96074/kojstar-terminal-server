package com.kojstarinnovations.terminal.commons.data.transport.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
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
 * PaymentRequest - Transport object for Payment with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PaymentRequest extends CommonsRequest {
    // Local assignment
    private Long cashId;
    private Integer creditCardDetailId;
    private Integer debitCardDetailId;
    private Integer checkDetailId;
    private Integer transferDetailId;

    @DataRequired(message = "Payment type is required")
    private String paymentType;

    @DataRequired(message = "Payment method is required")
    private String paymentMethod;

    private String transactionType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @DecimalRequired(message = "Total is required")
    private BigDecimal total;

    private BigDecimal totalCash;
    private BigDecimal totalCreditCard;
    private BigDecimal totalDebitCard;
    private BigDecimal totalCheck;
    private BigDecimal totalTransfer;

    private CashRequest cashRequest;
    private CreditCardDetailRequest creditCardDetailRequest;
    private DebitCardDetailRequest debitCardDetailRequest;
    private CheckDetailRequest checkDetailRequest;
    private TransferDetailRequest transferDetailRequest;
}