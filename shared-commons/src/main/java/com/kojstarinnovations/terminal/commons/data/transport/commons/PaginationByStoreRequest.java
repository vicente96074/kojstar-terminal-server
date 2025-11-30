package com.kojstarinnovations.terminal.commons.data.transport.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * PaginationByStoreRequest - Transport object for Pagination By Store with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PaginationByStoreRequest extends CommonsRequest {
    private String storeBranchId;
    private String storeId;
    private Integer page;
    private Integer size;
    private String orders;
    private Boolean asc;
}