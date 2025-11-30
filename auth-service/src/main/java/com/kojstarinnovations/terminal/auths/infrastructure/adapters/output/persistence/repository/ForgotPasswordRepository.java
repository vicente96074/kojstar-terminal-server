package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * ForgotPasswordRepository - This interface is used to persist, retrieve and manage ForgotPassword entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    /**
     * This method is used to find a forgot password by its token
     *
     * @param token The token of the forgot password
     * @return An optional of the forgot password
     */
    @Query("SELECT fp FROM forgot_password fp WHERE fp.token = :token")
    Optional<ForgotPassword> findByToken(@RequestParam("token") String token);

    /**
     * This method is used to consume a token
     *
     * @param token The token to be consumed
     */
    @Modifying
    @Query("UPDATE forgot_password fp SET fp.used = true WHERE fp.token = :token")
    void consumeToken(@RequestParam("token") String token);
}