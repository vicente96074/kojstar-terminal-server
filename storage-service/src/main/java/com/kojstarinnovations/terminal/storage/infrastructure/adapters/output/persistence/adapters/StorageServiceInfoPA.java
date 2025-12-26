package com.kojstarinnovations.terminal.storage.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.log.BaseLog;
import com.kojstarinnovations.terminal.storage.domain.opextends.StorageServiceInfoOP;
import com.kojstarinnovations.terminal.storage.infrastructure.adapters.output.persistence.mapper.StorageServiceInfoPM;
import com.kojstarinnovations.terminal.storage.infrastructure.adapters.output.persistence.repository.StorageServiceInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageServiceInfoPA implements StorageServiceInfoOP {

    @Override
    public void save(BaseLog log) {
        repository.save(mapper.dtoToDocument(log));
    }

    private final StorageServiceInfoRepository repository;
    private final StorageServiceInfoPM mapper;
}
