package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RolRepository - To persist, retribute and manage Rol entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 * Also, it has a custom method to find a rol by its name,
 * has a custom method to check if a rol exists by its name
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, String> {

    /**
     * This method is used to find a rol by its name
     *
     * @param rolName The name of the rol
     * @return An optional of the rol
     */
    Optional<Rol> findByRolName(RolName rolName);

    /**
     * This method is used to check if a rol exists by its name
     *
     * @param rolName The name of the rol
     * @return A boolean indicating if the rol exists
     */
    boolean existsByRolName(RolName rolName);
}
