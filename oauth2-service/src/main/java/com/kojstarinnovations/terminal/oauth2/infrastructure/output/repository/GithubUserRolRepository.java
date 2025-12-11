package com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository;

import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GithubUserRol;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.UserRolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubUserRolRepository extends JpaRepository<GithubUserRol, UserRolId> {
}