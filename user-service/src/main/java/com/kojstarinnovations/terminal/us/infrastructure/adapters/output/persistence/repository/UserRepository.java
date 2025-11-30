package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * UserRepository is used to access the database and perform CRUD operations on the user table
 *
 * @Author: Augusto Vicente and Kojstar Innovations
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * This method is used to find a user by its email
     *
     * @param username The email of the user
     * @return An optional of the user
     */
    boolean existsByUsername(String username);

    /**
     * This method is used to find a user by its email
     *
     * @param email The email of the user
     * @return An optional of the user
     */
    boolean existsByEmail(String email);

    /**
     * This method is used to find a user by its store id
     *
     * @param pageable The pageable object
     * @return A page of users
     */
    @Query("SELECT u FROM users u WHERE u.status = :status")
    Page<User> getFindByStatus(@Param("status") Status status, Pageable pageable);

    /**
     * Update user setting id
     *
     * @param id            the id of the user
     * @param userSettingId the setting id
     */
    @Modifying
    @Query("UPDATE users u SET u.userSettingId = :userSettingId WHERE u.id = :id")
    void updateUserSettingId(@Param("id") String id, @Param("userSettingId") String userSettingId);

    /**
     * Update user status
     *
     * @param id     the id of the user
     * @param status the status of the user
     */
    @Modifying
    @Query("UPDATE users u SET u.status = :status WHERE u.id = :id")
    void updateStatus(String id, Status status);

    /**
     * Find status by id
     *
     * @param userId the id of the user
     * @return the status of the user
     */
    @Query("SELECT u.status FROM users u WHERE u.id = :userId")
    Status findStatusById(@Param("userId") String userId);
}