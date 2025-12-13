package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.us.domain.model.UserServiceLogDTO;
import com.kojstarinnovations.terminal.us.domain.opextends.UserServiceLogOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.UserServiceLogPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.UserServiceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceLogPA implements UserServiceLogOP {

    @Override
    public void save(UserServiceLogDTO dto) {
        repository.save(
                mapper.dtoToDocument(dto)
        );
    }

    private final UserServiceLogRepository repository;
    private final UserServiceLogPM mapper;
}
