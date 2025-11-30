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
 * StoreBranchRequest - Transport object for Store Branch with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StoreBranchRequest extends CommonsRequest {

    @DataRequired(message = "Store id is required")
    private String storeId;

    @IdIntegerRequired(message = "Location id is required")
    private Integer locationId;

    @DataRequired(message = "Nit is required")
    private String nit;

    @DataRequired(message = "Name is required")
    private String name;

    @DataRequired(message = "Phone is required")
    private String phone;

    @DataRequired(message = "Email is required")
    private String email;

    @DataRequired(message = "Opening hours is required")
    private String openingHours;

    @DataRequired(message = "Closing hours is required")
    private String closingHours;

    private String filename;
    private String generalManager;
}