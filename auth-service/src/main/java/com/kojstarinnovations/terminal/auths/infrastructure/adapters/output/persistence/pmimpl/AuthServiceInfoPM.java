package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.document.AuthServiceInfo;
import com.kojstarinnovations.terminal.commons.data.log.BaseLog;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceInfoPM {

    public AuthServiceInfo dtoToDocument(BaseLog log) {
        return AuthServiceInfo.builder()
                .timestamp(log.getTimestamp())
                .userId(log.getUserId())
                .eventType(log.getEventType())
                .details(log.getDetails())
                .build();
    }
}