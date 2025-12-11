package com.kojstarinnovations.terminal.oauth2.domain.service;

import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.Access;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GithubUserAccess;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository.AccessRepository;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository.GithubUserAccessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GithubUserAccessService {

    public void saveList(List<AccessName> accessNames, String userId) {

        List<Access> accesses = accessRepository.findByAccessNameIn(accessNames);

        accesses.forEach(access -> repository.save(GithubUserAccess.builder()
                .accessId(access.getId())
                .userId(userId)
                .status(Status.ACTIVE)
                .build()));
    }

    private final GithubUserAccessRepository repository;
    private final AccessRepository accessRepository;
}