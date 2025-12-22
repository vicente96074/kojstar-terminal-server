package com.kojstarinnovations.terminal.commons.data.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseLog {
    private LocalDateTime timestamp;
    private String userId;
    private String eventType;
    private Map<String, Object> details;
}