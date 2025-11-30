package com.kojstarinnovations.terminal.commons.data.transport.branch;

import com.kojstarinnovations.terminal.commons.data.transport.commons.CommonsRequest;
import com.kojstarinnovations.terminal.commons.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * BrandRequest - Transport object for Brand with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BrandRequest extends CommonsRequest {

    @DataRequired(message = "Brand name is required")
    private String name;

    @DataRequired(message = "Brand acronym is required")
    private String acronym;

    @DataRequired(message = "Brand description is required")
    private String description;

    private String filename;
}