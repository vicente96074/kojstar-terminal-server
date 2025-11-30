package com.kojstarinnovations.terminal.us.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.shared.ports.input.UseCase;
import com.kojstarinnovations.terminal.us.application.data.request.ContactUSRequest;

import java.util.List;

/**
 * ContactUSUC interface for the ContactUS use case
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface ContactUSUC extends UseCase<ContactUSRequest, ContactUSResponse, String> {

    /**
     * Method to get all active phones by auth
     *
     * @return List<ContactUSResponse>
     */
    List<ContactUSResponse> getAllActivePhonesByAuth();

    /**
     * Validate if the phone number already exists by sub and country code
     *
     * @param phoneNumber the phone number to check
     * @param sub         the sub of the user
     * @param countryCode the country code to check
     * @return true if the phone number exists, false otherwise
     */
    boolean existPhoneBySubAndCountryCode(String phoneNumber, String sub, String countryCode);

    /**
     * Validate if the email already exists by sub
     *
     * @param email the email to check
     * @param sub   the sub of the user
     * @return true if the email exists, false otherwise
     */
    boolean existEmailBySub(String email, String sub);
}