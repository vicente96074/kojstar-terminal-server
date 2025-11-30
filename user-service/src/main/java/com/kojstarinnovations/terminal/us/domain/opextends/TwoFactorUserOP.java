package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.TwoFactorUserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorUserResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OP;

import java.util.List;
import java.util.Optional;

public interface TwoFactorUserOP extends OP<TwoFactorUserDTO, TwoFactorUserResponse, String> {

    /**
     * Get the two-factor app secret by user id
     *
     * @param userId the id of the user
     * @return the two factor secret
     */
    String getTwoFactorAppSecretByUserId(String userId);

    /**
     * Enable and activate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    void enableAndActiveTwoFactorAppByUserId(String userId);

    /**
     * Deactivate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    void deactivateTwoFactorAppByUserId(String userId);

    /**
     * Check if the user has any primary two-factor authentication
     *
     * @param userId the id of the user
     * @return true if the user has any primary two-factor authentication, false otherwise
     */
    boolean hasAnyPrimaryByUserId(String userId);

    /**
     * Enable and activate the two-factor SMS by user id
     *
     * @param sub the id of the user
     */
    void enableAndActiveTwoFactorSmsByUserId(String sub);

    /**
     * Verify if the user has any phone number registered
     *
     * @param sub the id of the user
     * @return true if the user has any phone number registered, false otherwise
     */
    boolean existsPhoneBySub(String sub);

    /**
     * Check if the phone number already exists by sub
     *
     * @param sub             the id of the user
     * @param fullPhoneNumber the full phone number to check
     * @return true if the phone number already exists, false otherwise
     */
    boolean matchPhoneBySub(String sub, String fullPhoneNumber, CountryCodeISO codeISO);

    /**
     * Check if an email already exists by sub
     *
     * @param sub the id of the user
     * @return true if the email already exists, false otherwise
     */
    boolean existEmailBySub(String sub);

    /**
     * Check if an email received match the one registered by sub
     *
     * @param sub   the id of the user
     * @param email the email to check
     * @return true if the email already exists, false otherwise
     */
    boolean matchEmailBySub(String sub, String email);

    /**
     * Enable and activate the two-factor email by user id
     *
     * @param sub the id of the user
     */
    void enableAndActiveTwoFactorEmailByUserId(String sub);

    /**
     * Get all two-factor authentication by sub
     *
     * @param sub the id of the user
     * @return a list of two-factor authentication
     */
    List<TwoFactorUserResponse> getAllTwoFactorBySub(String sub);

    /**
     * Get the two-factor authentication by sub and type
     *
     * @param sub           the id of the user
     * @param twoFactorType the type of two-factor authentication
     * @return the two-factor authentication
     */
    Optional<TwoFactorUserResponse> get2FABySubAndType(String sub, TwoFactorType twoFactorType);
}