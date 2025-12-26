package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.auths.domain.opextends.AuthServiceInfoOP;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.pmimpl.AuthServiceInfoPM;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.repository.AuthServiceInfoRepository;
import com.kojstarinnovations.terminal.commons.data.log.BaseLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceInfoPA implements AuthServiceInfoOP {

    @Override
    public void save(BaseLog log) {
        repository.save(mapper.dtoToDocument(log));
    }

    private final AuthServiceInfoRepository repository;
    private final AuthServiceInfoPM mapper;
}