package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.IdentificationUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * IdentificationUSRepository - To persist, retribute and manage IdentificationUS entities in the database,
 * extends JpaRepository to use the default methods for CRUD operations and pagination
 * Also, it has a custom method to check if entity exists by identification number and identification type
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface IdentificationUSRepository extends JpaRepository<IdentificationUS, String> {

    /**
     * Check if entity exists by identification number and identification type
     *
     * @param idNumber    the identification number
     * @param idType      the identification type
     * @param nationality the nationality
     * @return boolean
     */
    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END FROM identifications i WHERE i.identificationNumber = :idNumber AND i.identificationType = :idType AND i.nationality = :nationality")
    boolean existsByIDNumberAndIDTypeAndNationality(
            @Param("idNumber") String idNumber,
            @Param("idType") IdentificationType idType,
            @Param("nationality") CountryCodeISO nationality
    );
}