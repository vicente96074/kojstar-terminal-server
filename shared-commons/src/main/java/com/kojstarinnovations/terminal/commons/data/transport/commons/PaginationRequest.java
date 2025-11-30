package com.kojstarinnovations.terminal.commons.data.transport.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {
    private Integer page;
    private Integer size;
    private String orders;
    private Boolean asc;
}