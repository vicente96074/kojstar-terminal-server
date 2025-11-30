package com.kojstarinnovations.terminal.commons.data.helper;

import com.kojstarinnovations.terminal.commons.data.transport.payment.CashRequest;
import com.kojstarinnovations.terminal.commons.exception.InvalidDataException;

import java.math.BigDecimal;

/**
 * CashHelper - Helper to validate cash requests or do operations with cash
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public class CashHelper {

    /**
     * Evaluate if the cash is valid
     *
     * @param request   the cash
     * @param totalCash the total cash
     */
    public static void isCashValid(CashRequest request, BigDecimal totalCash) {
        if (request == null) {
            throw new InvalidDataException("El efectivo es requerido");
        }

        // Validate the cash denominations and coins
        BigDecimal total = BigDecimal.ZERO;
        total = total.add(DataHelper.intToBigDecimal(request.getCant1CentM()).multiply(BigDecimal.valueOf(0.01)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant5CentM()).multiply(BigDecimal.valueOf(0.05)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant10CentM()).multiply(BigDecimal.valueOf(0.10)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant25CentM()).multiply(BigDecimal.valueOf(0.25)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant50CentM()).multiply(BigDecimal.valueOf(0.50)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant50CentB()).multiply(BigDecimal.valueOf(0.50)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant1QuetzM()).multiply(BigDecimal.valueOf(1.00)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant1QuetzB()).multiply(BigDecimal.valueOf(1.00)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant5QuetzB()).multiply(BigDecimal.valueOf(5.00)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant10QuetzB()).multiply(BigDecimal.valueOf(10.00)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant20QuetzB()).multiply(BigDecimal.valueOf(20.00)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant50QuetzB()).multiply(BigDecimal.valueOf(50.00)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant100QuetzB()).multiply(BigDecimal.valueOf(100.00)));
        total = total.add(DataHelper.intToBigDecimal(request.getCant200QuetzB()).multiply(BigDecimal.valueOf(200.00)));

        if (total.compareTo(totalCash) != 0) {
            throw new InvalidDataException("El efectivo no coincide con el monto recibido");
        }
    }
}