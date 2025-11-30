package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.TwoFactorUserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorUserResponse;
import com.kojstarinnovations.terminal.us.domain.opextends.TwoFactorUserOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.TwoFactorUserPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.TwoFactorUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TwoFactorUserPA implements TwoFactorUserOP {

    /**
     * Method to save a modelDto
     *
     * @param dto the modelDto to be saved
     * @return modelDto
     */
    @Override
    public TwoFactorUserResponse save(TwoFactorUserDTO dto) {
        return pm.entityToResponse(repository.save(pm.dtoToEntity(dto)));
    }

    /**
     * Method to get a modelDto by id
     *
     * @param s the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    @Override
    public Optional<TwoFactorUserResponse> getById(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<TwoFactorUserResponse> getPage(Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<TwoFactorUserResponse> getAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to update a modelDto by id
     *
     * @param dto the modelDto to be updated
     * @param id  the id of the modelDto to be updated
     * @return modelDto updated
     */
    @Override
    public TwoFactorUserResponse updateById(TwoFactorUserDTO dto, String id) {
        return pm.entityToResponse(repository.save(pm.dtoToEntity(dto)));
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method to check if a modelDto exists by id
     *
     * @param id the id of the modelDto to be checked
     * @return true if the modelDto exists, false otherwise
     */
    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    /**
     * Get the two-factor app secret by user id
     *
     * @param userId the id of the user
     * @return the two factor secret
     */
    @Override
    public String getTwoFactorAppSecretByUserId(String userId) {
        return repository.findTwoFactorSecretAppByUserId(userId);
    }

    /**
     * Enable and activate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    @Override
    public void enableAndActiveTwoFactorAppByUserId(String userId) {
        repository.enableAndActiveTwoFactorAppByUserId(userId);
    }

    /**
     * Deactivate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    @Override
    public void deactivateTwoFactorAppByUserId(String userId) {
        repository.deactivateTwoFactorAppByUserId(userId);
    }

    /**
     * Check if the user has any primary two-factor authentication
     *
     * @param userId the id of the user
     * @return true if the user has any primary two-factor authentication, false otherwise
     */
    @Override
    public boolean hasAnyPrimaryByUserId(String userId) {
        return repository.hasAnyPrimaryByUserId(userId);
    }

    /**
     * Enable and activate the two-factor SMS by user id
     *
     * @param sub the id of the user
     */
    @Override
    public void enableAndActiveTwoFactorSmsByUserId(String sub) {
        repository.enableAndActiveTwoFactorSmsByUserId(sub);
    }

    /**
     * Verify if the user has any phone number registered
     *
     * @param sub the id of the user
     * @return true if the user has any phone number registered, false otherwise
     */
    @Override
    public boolean existsPhoneBySub(String sub) {
        return repository.existsPhoneByUserId(sub);
    }

    /**
     * Check if the phone number already exists by sub
     *
     * @param sub             the id of the user
     * @param fullPhoneNumber the full phone number to check
     * @return true if the phone number already exists, false otherwise
     */
    @Override
    public boolean matchPhoneBySub(String sub, String fullPhoneNumber, CountryCodeISO codeISO) {
        return repository.matchPhoneByUserId(sub, fullPhoneNumber, codeISO);
    }

    /**
     * Check if an email already exists by sub
     *
     * @param sub the id of the user
     * @return true if the email already exists, false otherwise
     */
    @Override
    public boolean existEmailBySub(String sub) {
        return repository.existEmailByUserId(sub);
    }

    /**
     * Check if an email received match the one registered by sub
     *
     * @param sub   the id of the user
     * @param email the email to check
     * @return true if the email already exists, false otherwise
     */
    @Override
    public boolean matchEmailBySub(String sub, String email) {
        return repository.matchEmailByUserId(sub, email);
    }

    /**
     * Enable and activate the two-factor email by user id
     *
     * @param sub the id of the user
     */
    @Override
    public void enableAndActiveTwoFactorEmailByUserId(String sub) {
        repository.enableAndActiveTwoFactorEmailByUserId(sub);
    }

    /**
     * Get all two-factor authentication by sub
     *
     * @param sub the id of the user
     * @return a list of two-factor authentication
     */
    @Override
    public List<TwoFactorUserResponse> getAllTwoFactorBySub(String sub) {
        return repository.getAllTwoFactorByUserId(sub)
                .stream()
                .map(pm::entityToResponse)
                .toList();
    }

    /**
     * Get the two-factor authentication by sub and type
     *
     * @param sub           the id of the user
     * @param twoFactorType the type of two-factor authentication
     * @return the two-factor authentication
     */
    @Override
    public Optional<TwoFactorUserResponse> get2FABySubAndType(String sub, TwoFactorType twoFactorType) {
        return repository.get2FAByUserIdAndType(sub, twoFactorType)
                .map(pm::entityToResponse);
    }

    private final TwoFactorUserRepository repository;
    private final TwoFactorUserPM pm;
}
