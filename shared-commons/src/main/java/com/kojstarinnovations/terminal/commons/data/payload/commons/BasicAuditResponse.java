package com.kojstarinnovations.terminal.commons.data.payload.commons;

import com.kojstarinnovations.terminal.commons.data.enums.ElementStatus;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuditResponse {
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ElementStatus elementStatus;
    private Status status;
}
