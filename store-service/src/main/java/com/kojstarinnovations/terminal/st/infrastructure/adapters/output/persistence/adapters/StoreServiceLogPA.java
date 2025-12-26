package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.st.domain.model.StoreServiceLogDTO;
import com.kojstarinnovations.terminal.st.domain.opextends.StoreServiceLogOP;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.pmimpl.StoreServiceLogPM;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.repository.StoreServiceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreServiceLogPA implements StoreServiceLogOP {

    @Override
    public void create(StoreServiceLogDTO dto) {
        repository.save(mapper.dtoToDocument(dto));
    }

    private final StoreServiceLogRepository repository;
    private final StoreServiceLogPM mapper;
}