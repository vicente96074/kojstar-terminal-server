package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.us.domain.model.UserServiceLogDTO;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.document.UserServiceLog;
import org.springframework.stereotype.Component;

@Component
public class UserServiceLogPM {

    public UserServiceLog dtoToDocument(UserServiceLogDTO dto) {
        return UserServiceLog.builder()
                .timestamp(dto.getTimestamp())
                .userId(dto.getUserId())
                .eventType(dto.getEventType())
                .details(dto.getDetails())
                .build();
    }
}
