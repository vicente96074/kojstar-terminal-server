package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AuthRepository - This interface is used to persist, retrieve and manage User entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface AuthRepository extends JpaRepository<User, String> {

    /**
     * This method is used to find a user by its username or email
     *
     * @param username the username of the user
     * @param email    the email of the user
     * @return An optional of the user
     */
    @Query("SELECT u FROM users u WHERE u.username = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM users u JOIN u.twoFactorUsers t WHERE u.id = :userId AND t.twoFactorActive = true")
    boolean hasAnyTwoFactorActive(@Param("userId") String userId);

    @Query("SELECT u FROM users u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
}