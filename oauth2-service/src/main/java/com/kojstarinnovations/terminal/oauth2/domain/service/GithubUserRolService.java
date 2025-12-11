package com.kojstarinnovations.terminal.oauth2.domain.service;

import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GithubUserRol;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.Rol;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository.GithubUserRolRepository;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository.RolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GithubUserRolService {

    public void saveList(List<RolName> rolNames, String userId) {

        List<Rol> roles = rolRepository.findByRolNameIn(rolNames);

        roles.forEach(rol -> repository.save(GithubUserRol.builder()
                .rolId(rol.getId())
                .userId(userId)
                .status(Status.ACTIVE)
                .build()));
    }

    private final GithubUserRolRepository repository;
    private final RolRepository rolRepository;
}