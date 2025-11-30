package com.kojstarinnovations.terminal.commons.data.transport.branch;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.DecimalRequired;
import com.kojstarinnovations.terminal.commons.validation.IdIntegerRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * StockRequest - Transport object for Stock with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StockRequest extends CommonsRequest {

    @IdIntegerRequired(message = "El id del producto es requerido para relacionar el producto en la tienda")
    private Integer commodityId;

    @DataRequired(message = "El id de la tienda es requerido para relacionar el producto en la tienda")
    private String storeBranchId;

    @DecimalRequired(message = "El precio de venta es requerido")
    private BigDecimal priceSale;

    @DecimalRequired(message = "El precio al por mayor es requerido")
    private BigDecimal priceWholesale;

    private Integer stock;

    @DecimalRequired(message = "El porcentaje de descuento es requerido")
    private BigDecimal percentDiscount;
}