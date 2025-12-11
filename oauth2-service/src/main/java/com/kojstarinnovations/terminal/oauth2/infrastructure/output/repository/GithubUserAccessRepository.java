package com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository;

import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GithubUserAccess;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.UserAccessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubUserAccessRepository extends JpaRepository<GithubUserAccess, UserAccessId> {
}