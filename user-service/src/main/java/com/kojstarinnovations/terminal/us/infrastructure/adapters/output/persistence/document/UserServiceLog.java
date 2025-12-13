package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_service_logs")
@TypeAlias("UserServiceLog")
public class UserServiceLog {

    @Id
    private String id;

    private LocalDateTime timestamp;
    private String userId;
    private String eventType;
    private Map<String, Object> details;
}