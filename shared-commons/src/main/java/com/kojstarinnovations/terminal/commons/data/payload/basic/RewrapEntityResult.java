package com.kojstarinnovations.terminal.commons.data.payload.basic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewrapEntityResult {
    private final int totalRecords = 0;
    private final int rewrappedRecords = 0;
    private final int fieldsRewrapped = 0;
    private final int errors = 0;

    @Override
    public String toString() {
        return String.format(
                "RewrapEntityResult{total=%d, rewrapped=%d, fields=%d, errors=%d}",
                totalRecords, rewrappedRecords, fieldsRewrapped, errors
        );
    }
}