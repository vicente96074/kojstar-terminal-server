package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.document;

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
@Document(collection = "auth_service_info")
@TypeAlias("AuthServiceInfo")
public class AuthServiceInfo {
    @Id
    private String id;

    private LocalDateTime timestamp;
    private String userId;
    private String eventType;
    private Map<String, Object> details;
}