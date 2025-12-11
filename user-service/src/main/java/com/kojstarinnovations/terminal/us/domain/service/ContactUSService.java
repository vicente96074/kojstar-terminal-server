package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nUserConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.ContactUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
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
import java.util.Optional;

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
                                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_CONTACT_COUNTRY_CODE_NOT_FOUND))
                ) :
                outputPort.existsByEmailAndContactType(request.getEmail(), ContactType.EMAIL);

        if (exists) {
            throw new DuplicateException(I18nUserConstants.EXCEPTION_CONTACT_DUPLICATE);
        }

        if (outputPort.existsByPrimaryContactAndUserId(request.getUserId()) && request.getPrimaryContact()) {
            throw new DuplicateException(I18nUserConstants.EXCEPTION_CONTACT_DUPLICATE_PRIMARY);
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

        return outputPort.save(dto);
    }

    /**
     * Delete an entity by its id with transactional support
     *
     * @param id the id of the entity to be deleted
     */
    @Override
    public void deleteById(String id) {
        outputPort.deleteById(id);
    }

    /**
     * Check if an object exists by its id
     *
     * @param s id of the object to be retrieved
     * @return boolean
     */
    @Override
    public boolean existsById(String s) {
        return outputPort.existsById(s);
    }

    /**
     * Get object by id
     *
     * @param id id of the object to be retrieved
     * @return QueryResponse
     */
    @Override
    public ContactUSResponse getById(String id) {
        return outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_CONTACT_NOT_FOUND_BY_ID));
    }

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<ContactUSResponse> getPage(Pageable pageable) {
        return Optional.of(outputPort.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_CONTACT_PAGE_NOT_FOUND));
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<ContactUSResponse> getAll() {
        return Optional.of(outputPort.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_CONTACT_ALL_NOT_FOUND));
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id      id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public ContactUSResponse updateById(ContactUSRequest request, String id) {
        return outputPort.updateById(domainMapper.requestToDTO(request), id);
    }

    /**
     * Method to get all active phones by auth
     *
     * @return List<ContactUSResponse>
     */
    @Override
    public List<ContactUSResponse> getAllActivePhonesByAuth() {
        return Optional.of(outputPort.getAllActivePhonesBySub(userDetailsService.getUserFromAuth().getSub()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_CONTACT_ALL_NOT_FOUND_BY_SESSION));
    }

    @Override
    public boolean existPhoneBySubAndCountryCode(String phoneNumber, String sub, String countryCode) {
        CountryCodeISO countryCodeISO = CountryCodeISO.fromCode(countryCode)
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_CONTACT_COUNTRY_CODE_NOT_FOUND));

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