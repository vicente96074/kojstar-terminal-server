package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.ContactUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.us.application.data.request.ContactUSRequest;
import com.kojstarinnovations.terminal.us.domain.dmimpl.ContactUSDM;
import com.kojstarinnovations.terminal.us.domain.opextends.ContactUSOP;
import com.kojstarinnovations.terminal.us.domain.ucextends.ContactUSUC;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContactUSService implements ContactUSUC {


    /**
     * Use case to save a request with transactional support
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public ContactUSResponse save(ContactUSRequest request) {
        ContactType type = ContactType.valueOf(request.getContactType());
        boolean exists = type == ContactType.PHONE ?
                outputPort.existsByPhoneNumberAndContactTypeAndCountryCode(
                        request.getPhoneNumber(),
                        ContactType.PHONE,
                        CountryCodeISO.fromCode(request.getCountryCode())
                                .orElseThrow(() -> new DuplicateException(ExceptionConstants.COUNTRY_CODE_NOT_FOUND))
                ) :
                outputPort.existsByEmailAndContactType(request.getEmail(), ContactType.EMAIL);

        if (exists) {
            throw new DuplicateException(ExceptionConstants.DUPLICATE_CONTACT);
        }

        if (outputPort.existsByPrimaryContactAndUserId(request.getUserId()) && request.getPrimaryContact()) {
            throw new DuplicateException(ExceptionConstants.DUPLICATE_PRIMARY_CONTACT);
        }

        if (type == ContactType.PHONE) {
            request.setEmail(null);
        }

        if (type == ContactType.EMAIL) {
            request.setPhoneNumber(null);
            request.setCountryCode(null);
            request.setRegionCode(null);
        }

        ContactUSDTO dto = domainMapper.requestToDTO(request);
        dto.setId(null);
        dto.setStatus(Status.ACTIVE);
        ContactUSResponse response = outputPort.save(dto);

        return response;
    }

    /**
     * Delete an entity by its id with transactional support
     *
     * @param s the id of the entity to be deleted
     */
    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Check if an object exists by its id
     *
     * @param s id of the object to be retrieved
     * @return boolean
     */
    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get object by id
     *
     * @param s id of the object to be retrieved
     * @return QueryResponse
     */
    @Override
    public ContactUSResponse getById(String s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<ContactUSResponse> getPage(Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<ContactUSResponse> getAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param s       id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public ContactUSResponse updateById(ContactUSRequest request, String s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to get all active phones by auth
     *
     * @return List<ContactUSResponse>
     */
    @Override
    public List<ContactUSResponse> getAllActivePhonesByAuth() {
        return outputPort.getAllActivePhonesBySub(userDetailsService.getUserFromAuth().getSub());
    }

    /**
     * Validate if the phone number already exists by sub and country code
     *
     * @param phoneNumber the phone number to check
     * @param sub         the sub of the user
     * @param countryCode the country code to check
     * @return true if the phone number exists, false otherwise
     */
    @Override
    public boolean existPhoneBySubAndCountryCode(String phoneNumber, String sub, String countryCode) {
        CountryCodeISO countryCodeISO = CountryCodeISO.fromCode(countryCode)
                .orElseThrow(() -> new DuplicateException(ExceptionConstants.COUNTRY_CODE_NOT_FOUND));

        return outputPort.existsPhoneBySubAndCountryCode(
                phoneNumber,
                sub,
                countryCodeISO
        );
    }

    /**
     * Validate if the email already exists by sub
     *
     * @param email the email to check
     * @param sub   the sub of the user
     * @return true if the email exists, false otherwise
     */
    @Override
    public boolean existEmailBySub(String email, String sub) {
        return outputPort.existsEmailBySub(
                email,
                sub
        );
    }

    private final ContactUSOP outputPort;
    private final ContactUSDM domainMapper;
    private final UserDetailsServiceImpl userDetailsService;
}