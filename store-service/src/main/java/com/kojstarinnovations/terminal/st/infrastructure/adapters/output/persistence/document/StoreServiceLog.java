package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("StoreServiceLog")
@Document(collection = "store_service_logs")
public class StoreServiceLog {

    @Id
    private String id;
    private LocalDateTime timestamp;
    private String userId;
    private String eventType;
    private Map<String, Object> details;
}