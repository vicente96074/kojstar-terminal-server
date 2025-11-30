package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserRol;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserRolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRolRepository - To persist, retribute and manage UserRol entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 * Also, it has a custom method to delete by user id
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface UserRolRepository extends JpaRepository<UserRol, UserRolId> {

    /**
     * deleteByUserId method
     *
     * @param userId the userId to be deleted
     */
    void deleteByUserId(String userId);
}