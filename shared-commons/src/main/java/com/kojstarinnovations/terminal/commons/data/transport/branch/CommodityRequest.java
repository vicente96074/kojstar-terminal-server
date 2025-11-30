package com.kojstarinnovations.terminal.commons.data.transport.branch;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import com.kojstarinnovations.terminal.commons.validation.IdIntegerRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * CommodityRequest - Transport object for Commodity with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CommodityRequest extends CommonsRequest {

    @DataRequired(message = "Store ID is required")
    private String storeId;

    @IdIntegerRequired(message = "Unit of Measurement ID is required")
    private Integer unitOfMeasurementId;

    @IdIntegerRequired(message = "Category ID is required")
    private Integer categoryId;

    @IdIntegerRequired(message = "Brand ID is required")
    private Integer brandId;

    @DataRequired(message = "Barcode is required")
    private String barcode;

    @DataRequired(message = "Name is required")
    private String name;

    @DataRequired(message = "Description is required")
    private String description;

    @DataRequired(message = "Active Principle is required")
    private String activePrinciple;

    private String filename;
}