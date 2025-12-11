package com.kojstarinnovations.terminal.oauth2.domain.service;

import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GoogleUserRol;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.Rol;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository.GoogleUserRolRepository;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository.RolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleUserRolService {

    public void saveList(List<RolName> accessNames, String userId) {

        List<Rol> roles = rolRepository.findByRolNameIn(accessNames);

        roles.forEach(rol -> repository.save(GoogleUserRol.builder()
                .rolId(rol.getId())
                .userId(userId)
                .status(Status.ACTIVE)
                .build()));
    }

    private final GoogleUserRolRepository repository;
    private final RolRepository rolRepository;
}