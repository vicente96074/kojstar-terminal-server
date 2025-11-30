package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.ContactUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ContactUSRepository interface for the ContactUS entity
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface ContactUSRepository extends JpaRepository<ContactUS, String> {

    /**
     * Method to check if a phone number exists for a given contact type and country code
     *
     * @param phoneNumber    the phone number to check
     * @param contactType    the contact type to check
     * @param countryCodeISO the country code to check
     * @return true if the phone number exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contacts c WHERE c.phoneNumber = :phoneNumber AND c.contactType = :contactType AND c.countryCodeISO = :countryCodeISO")
    boolean existsByPhoneNumberAndContactTypeAndCountryCode(
            @Param("phoneNumber") String phoneNumber,
            @Param("contactType") ContactType contactType,
            @Param("countryCodeISO") CountryCodeISO countryCodeISO
    );

    /**
     * Method to check if an email exists for a given contact type
     *
     * @param email       the email to check
     * @param contactType the contact type to check
     * @return true if the email exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contacts c WHERE c.email = :email AND c.contactType = :contactType")
    boolean existsByEmailAndContactType(@Param("email") String email, @Param("contactType") ContactType contactType);

    /**
     * Method to check if a primary contact exists for a given user id
     *
     * @param userId the user id to check
     * @return true if the primary contact exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contacts c WHERE c.primaryContact = true AND c.userId = :userId")
    boolean existsByPrimaryContactAndUserId(@Param("userId") String userId);

    /**
     * Method to get all active phones by user id
     *
     * @param userId the user id to check
     * @return List<ContactUS>
     */
    @Query("SELECT c FROM contacts c WHERE c.userId = :userId AND c.status = 'ACTIVE' AND c.contactType = 'PHONE'")
    List<ContactUS> findAllActivePhonesByUserId(@Param("userId") String userId);

    /**
     * Validate if the phone number already exists by sub and country code
     *
     * @param phoneNumber the phone number to check
     * @param userId      the sub of the user
     * @param countryCode the country code to check
     * @return true if the phone number exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contacts c WHERE c.phoneNumber = :phoneNumber AND c.userId = :userId AND c.countryCodeISO = :countryCode AND c.contactType = 'PHONE'")
    boolean existsPhoneByUserIdAndCountryCode(@Param("phoneNumber") String phoneNumber, @Param("userId") String userId, @Param("countryCode") CountryCodeISO countryCode);

    /**
     * Validate if the email already exists by sub
     *
     * @param email  the email to check
     * @param userId the sub of the user
     * @return true if the email exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contacts c WHERE c.email = :email AND c.userId = :userId AND c.contactType = 'EMAIL'")
    boolean existsEmailByUserId(@Param("email") String email, @Param("userId") String userId);

    /**
     * Validate if any phone number already exists by sub
     *
     * @param userId the sub of the user
     * @return true if any phone number exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contacts c WHERE c.userId = :userId AND c.contactType = 'PHONE'")
    boolean existsPhoneByUserId(@Param("userId") String userId);
}