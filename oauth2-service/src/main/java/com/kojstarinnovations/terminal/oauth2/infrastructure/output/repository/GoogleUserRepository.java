package com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository;

import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUser, String> {
    Optional<GoogleUser> findByEmail(String email);

    boolean existsByEmail(String email);
}