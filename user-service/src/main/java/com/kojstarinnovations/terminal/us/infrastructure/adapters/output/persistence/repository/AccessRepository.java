package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AccessRepository - To persist, retribute and manage Access entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 * Also, it has a custom method to find Access by its name
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface AccessRepository extends JpaRepository<Access, String> {

    /**
     * Find Access by its name
     *
     * @param accessName the name of the access
     * @return the access found
     */
    Optional<Access> findByAccessName(AccessName accessName);

    /**
     * Check if Access exists by its name
     *
     * @param accessName the name of the access
     * @return true if the access exists, false otherwise
     */
    boolean existsByAccessName(AccessName accessName);
}