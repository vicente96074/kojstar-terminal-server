package com.kojstarinnovations.terminal.us.domain.model;

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
public class UserServiceLogDTO {
    private LocalDateTime timestamp;
    private String userId;
    private String eventType;
    private Map<String, Object> details;
}
