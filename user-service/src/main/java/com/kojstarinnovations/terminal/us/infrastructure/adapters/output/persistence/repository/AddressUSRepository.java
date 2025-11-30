package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.AddressUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AddressUSRepository - To persist, retribute and manage AddressUS entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface AddressUSRepository extends JpaRepository<AddressUS, String> {
}