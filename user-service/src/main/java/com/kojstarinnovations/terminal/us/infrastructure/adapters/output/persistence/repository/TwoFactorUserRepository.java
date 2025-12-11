package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.TwoFactorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for TwoFactorUser entity.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Repository
public interface TwoFactorUserRepository extends JpaRepository<TwoFactorUser, String> {

    /**
     * Find the two-factor secret for the authenticator app by userId.
     *
     * @param userId the userId to search for
     * @return the two-factor secret for the authenticator app
     */
    @Query("SELECT t.twoFactorSecret FROM two_factor t WHERE t.userId = :userId and t.twoFactorType = 'AUTHENTICATOR_APP'")
    String findTwoFactorSecretAppByUserId(@Param("userId") String userId);

    /**
     * Enable and activate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    @Modifying
    @Query("UPDATE two_factor t SET t.twoFactorEnabled = true, t.twoFactorActive = true WHERE t.userId = :userId AND t.twoFactorType = 'AUTHENTICATOR_APP'")
    void enableAndActiveTwoFactorAppByUserId(@Param("userId") String userId);

    /**
     * Deactivate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    @Modifying
    @Query("UPDATE two_factor t SET t.twoFactorActive = false WHERE t.userId = :userId AND t.twoFactorType = 'AUTHENTICATOR_APP'")
    void deactivateTwoFactorAppByUserId(@Param("userId") String userId);

    /**
     * Check if the user has any primary two-factor authentication method
     *
     * @param userId the id of the user
     * @return true if the user has any primary two-factor authentication method, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM two_factor t WHERE t.userId = :userId AND t.isPrimary = true")
    boolean hasAnyPrimaryByUserId(@Param("userId") String userId);

    /**
     * Enable and activate the two-factor SMS by user id
     *
     * @param userId the id of the user
     */
    @Modifying
    @Query("UPDATE two_factor t SET t.twoFactorEnabled = true, t.twoFactorActive = true WHERE t.userId = :userId AND t.twoFactorType = 'AUTHENTICATOR_SMS'")
    void enableAndActiveTwoFactorSmsByUserId(@Param("userId") String userId);

    /**
     * Check if the user has any phone number registered
     *
     * @param userId the id of the user
     * @return true if the user has any phone number registered, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM two_factor t WHERE t.userId = :userId AND t.twoFactorType = 'AUTHENTICATOR_SMS'")
    boolean existsPhoneByUserId(@Param("userId") String userId);
    
    /**
     * Check if an email already exists by userId
     *
     * @param userId the id of the user
     * @return true if the email already exists, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM two_factor t WHERE t.userId = :userId AND t.twoFactorType = 'EMAIL'")
    boolean existEmailByUserId(@Param("userId") String userId);

    /**
     * Check if an email received match the one registered by userId
     *
     * @param userId the id of the user
     * @param email  the email to check
     * @return true if the email already exists, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM two_factor t WHERE t.userId = :userId AND t.twoFactorEmail = :email")
    boolean matchEmailByUserId(@Param("userId") String userId, @Param("email") String email);

    /**
     * Enable and activate the two-factor email by user id
     *
     * @param userId the id of the user
     */
    @Modifying
    @Query("UPDATE two_factor t SET t.twoFactorEnabled = true, t.twoFactorActive = true WHERE t.userId = :userId AND t.twoFactorType = 'EMAIL'")
    void enableAndActiveTwoFactorEmailByUserId(@Param("userId") String userId);

    /**
     * Get all two-factor authentication methods by user id
     *
     * @param userid the id of the user
     * @return the list of two-factor authentication methods
     */
    @Query("SELECT t FROM two_factor t WHERE t.userId = :userId")
    List<TwoFactorUser> getAllTwoFactorByUserId(@Param("userId") String userid);

    /**
     * Get all two-factor authentication methods by user id and type
     *
     * @param userId        the id of the user
     * @param twoFactorType the type of the two-factor authentication
     * @return the list of two-factor authentication methods
     */
    @Query("SELECT t FROM two_factor t WHERE t.userId = :userId AND t.twoFactorType = :twoFactorType")
    Optional<TwoFactorUser> get2FAByUserIdAndType(@Param("userId") String userId, @Param("twoFactorType") TwoFactorType twoFactorType);
}