package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.enums.TwoFactorType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorStatus;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.TwoFactorUserResponse;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.TwoFactorUserRequest;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.us.domain.dmimpl.TwoFactorUserDM;
import com.kojstarinnovations.terminal.us.domain.opextends.TwoFactorUserOP;
import com.kojstarinnovations.terminal.us.domain.ucextends.TwoFactorUserUC;
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
public class TwoFactorUserService implements TwoFactorUserUC {

    /**
     * Use case to save a request with transactional support
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public TwoFactorUserResponse save(TwoFactorUserRequest request) {
        return outputPort.save(
                domainMapper.requestToDTO(request)
        );
    }

    /**
     * Delete an entity by its id with transactional support
     *
     * @param id the id of the entity to be deleted
     */
    @Override
    public void deleteById(String id) {
        if (!outputPort.existsById(id)) {
            throw new NotFoundException(ExceptionConstants.TWO_FACTOR_NOT_FOUND);
        }
        outputPort.deleteById(id);
    }

    /**
     * Check if an object exists by its id
     *
     * @param id id of the object to be retrieved
     * @return boolean
     */
    @Override
    public boolean existsById(String id) {
        return outputPort.existsById(id);
    }

    /**
     * Get object by id
     *
     * @param id id of the object to be retrieved
     * @return QueryResponse
     */
    @Override
    public TwoFactorUserResponse getById(String id) {
        return outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.TWO_FACTOR_NOT_FOUND));
    }

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<TwoFactorUserResponse> getPage(Pageable pageable) {
        return outputPort.getPage(pageable);
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<TwoFactorUserResponse> getAll() {
        return outputPort.getAll();
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id      id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public TwoFactorUserResponse updateById(TwoFactorUserRequest request, String id) {
        return outputPort.updateById(domainMapper.requestToDTO(request), id);
    }

    /**
     * Get the two-factor app secret by user id
     *
     * @param userId the id of the user
     * @return the two factor secret
     */
    @Override
    public String getTwoFactorAppSecretByUserId(String userId) {
        return outputPort.getTwoFactorAppSecretByUserId(userId);
    }

    /**
     * Enable and activate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    @Override
    public void enableAndActiveTwoFactorAppByUserId(String userId) {
        outputPort.enableAndActiveTwoFactorAppByUserId(userId);
    }

    /**
     * Deactivate the two-factor app by user id
     *
     * @param userId the id of the user
     */
    @Override
    public void deactivateTwoFactorAppByUserId(String userId) {
        outputPort.deactivateTwoFactorAppByUserId(userId);
    }

    /**
     * Check if the user has any primary two-factor authentication
     *
     * @param userId the id of the user
     * @return true if the user has any primary two-factor authentication, false otherwise
     */
    @Override
    public boolean hasAnyPrimaryByUserId(String userId) {
        return outputPort.hasAnyPrimaryByUserId(userId);
    }

    /**
     * Enable and activate the two-factor SMS by user id
     *
     * @param sub the id of the user
     */
    @Override
    public void enableAndActiveTwoFactorSmsByUserId(String sub) {
        outputPort.enableAndActiveTwoFactorSmsByUserId(
                sub
        );
    }

    /**
     * Validate if any phone number already exists by sub
     *
     * @param sub the sub of the user
     * @return true if any phone number exists, false otherwise
     */
    @Override
    public boolean existPhoneBySub(String sub) {
        return outputPort.existsPhoneBySub(
                sub
        );
    }

    /**
     * Check if the phone number already exists by sub
     *
     * @param sub             the id of the user
     * @param fullPhoneNumber the full phone number to check
     * @return true if the phone number already exists, false otherwise
     */
    @Override
    public boolean matchPhoneBySubAndCountryCode(String sub, String fullPhoneNumber, CountryCodeISO codeISO) {
        return outputPort.matchPhoneBySub(
                sub,
                fullPhoneNumber,
                codeISO
        );
    }

    /**
     * Check if an email already exists by sub
     *
     * @param sub the id of the user
     * @return true if the email already exists, false otherwise
     */
    @Override
    public boolean existEmailBySub(String sub) {
        return outputPort.existEmailBySub(
                sub
        );
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
        return outputPort.matchEmailBySub(
                sub,
                email
        );
    }

    /**
     * Enable and activate the two-factor email by user id
     *
     * @param sub the id of the user
     */
    @Override
    public void enableAndActiveTwoFactorEmailByUserId(String sub) {
        outputPort.enableAndActiveTwoFactorEmailByUserId(
                sub
        );
    }

    /**
     * Get all two-factor authentication and status by sub
     *
     * @param sub the id of the user
     * @return the two-factor status
     */
    @Override
    public TwoFactorStatus getAllTwoFactorBySub(String sub) {
        TwoFactorStatus twoFactorStatus = TwoFactorStatus.builder().build();
        List<TwoFactorUserResponse> list = outputPort.getAllTwoFactorBySub(
                sub
        );

        list.forEach(tfu -> {
            if (tfu.getTwoFactorType() == TwoFactorType.AUTHENTICATOR_EMAIL) {
                twoFactorStatus.setEmailEnabled(tfu.getTwoFactorEnabled() != null ? tfu.getTwoFactorEnabled() : false);
                twoFactorStatus.setEmailActive(tfu.getTwoFactorActive() != null ? tfu.getTwoFactorActive() : false);
                twoFactorStatus.setEmail(tfu.getTwoFactorEmail() != null ? tfu.getTwoFactorEmail() : "");
            }

            if (tfu.getTwoFactorType() == TwoFactorType.AUTHENTICATOR_SMS) {
                twoFactorStatus.setSmsEnabled(tfu.getTwoFactorEnabled() != null ? tfu.getTwoFactorEnabled() : false);
                twoFactorStatus.setSmsActive(tfu.getTwoFactorActive() != null ? tfu.getTwoFactorActive() : false);
                twoFactorStatus.setPhoneNumber(tfu.getTwoFactorPhone() != null ? tfu.getTwoFactorPhone() : "");
                twoFactorStatus.setPhoneNumberCountryCode(tfu.getTwoFactorCountryCode() != null ? tfu.getTwoFactorCountryCode().getCode() : CountryCodeISO.GT.getCode());
            }

            if (tfu.getTwoFactorType() == TwoFactorType.AUTHENTICATOR_APP) {
                twoFactorStatus.setAppEnabled(tfu.getTwoFactorEnabled() != null ? tfu.getTwoFactorEnabled() : false);
                twoFactorStatus.setAppActive(tfu.getTwoFactorActive() != null ? tfu.getTwoFactorActive() : false);
            }
        });

        return twoFactorStatus;
    }

    /**
     * Get the two-factor authentication sms by sub
     *
     * @param sub the id of the user
     * @return the two-factor user
     */
    @Override
    public TwoFactorUserResponse get2FASmsBySub(String sub) {
        return outputPort.get2FABySubAndType(
                sub,
                TwoFactorType.AUTHENTICATOR_SMS
        ).orElse(null);
    }

    private final TwoFactorUserOP outputPort;
    private final TwoFactorUserDM domainMapper;
}