package com.kojstarinnovations.terminal.storage.infrastructure.adapters.output.persistence.mapper;

import com.kojstarinnovations.terminal.commons.data.log.BaseLog;
import com.kojstarinnovations.terminal.storage.infrastructure.adapters.output.persistence.document.StorageServiceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageServiceInfoPM {

    public StorageServiceInfo dtoToDocument(BaseLog log){
        return StorageServiceInfo.builder()
                .timestamp(log.getTimestamp())
                .userId(log.getUserId())
                .eventType(log.getEventType())
                .details(log.getDetails())
                .build();
    }
}