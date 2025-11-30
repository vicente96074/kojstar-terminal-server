package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.ContactUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.ContactType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.ContactUSResponse;
import com.kojstarinnovations.terminal.us.domain.opextends.ContactUSOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.ContactUSPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.ContactUSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactUSPA implements ContactUSOP {

    /**
     * Method to save a modelDto
     *
     * @param dto the modelDto to be saved
     * @return modelDto
     */
    @Override
    public ContactUSResponse save(ContactUSDTO dto) {
        return persistenceMapper.entityToResponse(
                repository.save(persistenceMapper.dtoToEntity(dto))
        );
    }

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    @Override
    public Optional<ContactUSResponse> getById(String id) {
        return repository.findById(id)
                .map(persistenceMapper::entityToResponse);
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<ContactUSResponse> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(persistenceMapper::entityToResponse);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<ContactUSResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(persistenceMapper::entityToResponse)
                .toList();
    }

    /**
     * Method to update a modelDto by id
     *
     * @param contactUSDTO the modelDto to be updated
     * @param s            the id of the modelDto to be updated
     * @return modelDto updated
     */
    @Override
    public ContactUSResponse updateById(ContactUSDTO contactUSDTO, String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param s the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method to check if a modelDto exists by id
     *
     * @param s the id of the modelDto to be checked
     * @return true if the modelDto exists, false otherwise
     */
    @Override
    public boolean existsById(String s) {
        return repository.existsById(s);
    }

    /**
     * Method to check if a phone number exists for a given contact type
     *
     * @param phoneNumber    the phone number to check
     * @param contactType    the contact type to check
     * @param countryCodeISO the country code to check
     * @return true if the phone number exists, false otherwise
     */
    @Override
    public boolean existsByPhoneNumberAndContactTypeAndCountryCode(String phoneNumber, ContactType contactType, CountryCodeISO countryCodeISO) {
        return repository.existsByPhoneNumberAndContactTypeAndCountryCode(phoneNumber, contactType, countryCodeISO);
    }

    /**
     * Method to check if an email exists for a given contact type
     *
     * @param email       the email to check
     * @param contactType the contact type to check
     * @return true if the email exists, false otherwise
     */
    @Override
    public boolean existsByEmailAndContactType(String email, ContactType contactType) {
        return repository.existsByEmailAndContactType(email, contactType);
    }

    /**
     * Method to check if a primary contact exists for a given user id
     *
     * @param userId the user id to check
     * @return true if the primary contact exists, false otherwise
     */
    @Override
    public boolean existsByPrimaryContactAndUserId(String userId) {
        return repository.existsByPrimaryContactAndUserId(userId);
    }

    /**
     * Method to get all active phones by auth
     *
     * @return List<ContactUSResponse>
     */
    @Override
    public List<ContactUSResponse> getAllActivePhonesBySub(String sub) {
        return repository.findAllActivePhonesByUserId(sub)
                .stream()
                .map(persistenceMapper::entityToResponse)
                .toList();
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
    public boolean existsPhoneBySubAndCountryCode(String phoneNumber, String sub, CountryCodeISO countryCode) {
        return repository.existsPhoneByUserIdAndCountryCode(phoneNumber, sub, countryCode);
    }

    /**
     * Validate if the email already exists by sub
     *
     * @param email the email to check
     * @param sub   the sub of the user
     * @return true if the email exists, false otherwise
     */
    @Override
    public boolean existsEmailBySub(String email, String sub) {
        return repository.existsEmailByUserId(email, sub);
    }

    /**
     * Validate if any phone number already exists by sub
     *
     * @param sub the sub of the user
     * @return true if any phone number exists, false otherwise
     */
    @Override
    public boolean existsPhoneBySub(String sub) {
        return repository.existsPhoneByUserId(sub);
    }

    private final ContactUSRepository repository;
    private final ContactUSPM persistenceMapper;
}