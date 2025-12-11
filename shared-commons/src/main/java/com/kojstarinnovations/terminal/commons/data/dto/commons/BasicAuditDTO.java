package com.kojstarinnovations.terminal.commons.data.dto.commons;

import com.kojstarinnovations.terminal.commons.data.enums.ElementStatus;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BasicAuditDTO {
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ElementStatus elementStatus;
    private Status status;
}
