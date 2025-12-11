package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorStatus;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorUserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.TwoFactorUserRequest;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;

public interface TwoFactorUserUC extends UseCase<TwoFactorUserRequest, TwoFactorUserResponse, String> {

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
     * Detect if the user has any phone number registered
     *
     * @param sub the id of the user
     * @return true if the user has any phone number registered, false otherwise
     */
    boolean existPhoneBySub(String sub);
    
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
     * Get all two-factor authentication and status by sub
     *
     * @param sub the id of the user
     * @return the two-factor status
     */
    TwoFactorStatus getAllTwoFactorBySub(String sub);

    /**
     * Get the two-factor authentication sms by sub
     *
     * @param sub the id of the user
     * @return the two-factor user
     */
    TwoFactorUserResponse get2FASmsBySub(String sub);
}