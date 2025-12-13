package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.st.domain.model.StoreServiceLogDTO;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.document.StoreServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreServiceLogPM {

    public StoreServiceLog dtoToDocument(StoreServiceLogDTO dto) {
        return StoreServiceLog.builder()
                .timestamp(dto.getTimestamp())
                .userId(dto.getUserId())
                .eventType(dto.getEventType())
                .details(dto.getDetails())
                .build();
    }
}