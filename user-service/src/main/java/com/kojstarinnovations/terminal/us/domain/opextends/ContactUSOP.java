package com.kojstarinnovations.terminal.us.domain.opextends;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.ContactUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.shared.ports.output.OutputPort;

import java.util.List;

/**
 * ContactUSOP interface for the ContactUS use case
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface ContactUSOP extends OutputPort<ContactUSDTO, ContactUSResponse, String> {

    /**
     * Method to check if a phone number exists for a given contact type
     *
     * @param phoneNumber    the phone number to check
     * @param contactType    the contact type to check
     * @param countryCodeISO the country code to check
     * @return true if the phone number exists, false otherwise
     */
    boolean existsByPhoneNumberAndContactTypeAndCountryCode(
            String phoneNumber,
            ContactType contactType,
            CountryCodeISO countryCodeISO
    );

    /**
     * Method to check if an email exists for a given contact type
     *
     * @param email       the email to check
     * @param contactType the contact type to check
     * @return true if the email exists, false otherwise
     */
    boolean existsByEmailAndContactType(String email, ContactType contactType);

    /**
     * Method to check if a primary contact exists for a given user id
     *
     * @param userId the user id to check
     * @return true if the primary contact exists, false otherwise
     */
    boolean existsByPrimaryContactAndUserId(String userId);

    /**
     * Method to get all active phones by auth
     *
     * @return List<ContactUSResponse>
     */
    List<ContactUSResponse> getAllActivePhonesBySub(String sub);

    /**
     * Validate if the phone number already exists by sub and country code
     *
     * @param phoneNumber    the phone number to check
     * @param sub            the sub of the user
     * @param countryCodeISO the country code to check
     * @return true if the phone number exists, false otherwise
     */
    boolean existsPhoneBySubAndCountryCode(String phoneNumber, String sub, CountryCodeISO countryCodeISO);

    /**
     * Validate if the email already exists by sub
     *
     * @param email the email to check
     * @param sub   the sub of the user
     * @return true if the email exists, false otherwise
     */
    boolean existsEmailBySub(String email, String sub);

}