package com.kojstarinnovations.terminal.st.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreServiceLogDTO {
    private LocalDateTime timestamp;
    private String userId;
    private String eventType;
    private Map<String, Object> details;
}
