package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserAccess;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserAccessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserAccessRepository - To persist, retribute and manage UserAccess entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 * Also, it has a custom method to delete by user id
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, UserAccessId> {

    /**
     * Delete by user id
     *
     * @param userId the user id
     */
    void deleteByUserId(String userId);
}