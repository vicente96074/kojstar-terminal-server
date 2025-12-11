package com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository;

import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GithubUserRepository extends JpaRepository<GithubUser, String> {
    boolean existsByEmail(String email);

    Optional<GithubUser> findByEmail(String email);
}