package com.kojstarinnovations.terminal.st.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nAuthConstants;
import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import com.kojstarinnovations.terminal.commons.data.enums.ElementStatus;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.exception.UserNotFoundException;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditAttributeService {

    public BasicAuditDTO getAuditAttributesForNew(BasicAuditDTO dto) {
        PrincipalUser user = userDetailsService.getUserFromAuth();

        if (user == null) {
            throw new UserNotFoundException(I18nAuthConstants.EXCEPTION_USER_NOT_AUTHENTICATED);
        }

        dto.setCreatedBy(user.getSub());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setElementStatus(ElementStatus.NEW);
        dto.setStatus(Status.ACTIVE);
        return dto;
    }

    public BasicAuditDTO getAuditAttributesForMigration(BasicAuditDTO dto) {
        PrincipalUser user = userDetailsService.getUserFromAuth();

        if (user == null) {
            throw new UserNotFoundException(I18nAuthConstants.EXCEPTION_USER_NOT_AUTHENTICATED);
        }

        dto.setCreatedBy(user.getSub());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setElementStatus(ElementStatus.MIGRATED);
        dto.setStatus(Status.ACTIVE);
        return dto;
    }

    public BasicAuditDTO getAuditAttributesForUpdate(BasicAuditDTO dto) {
        PrincipalUser user = userDetailsService.getUserFromAuth();

        if (user == null) {
            throw new UserNotFoundException(I18nAuthConstants.EXCEPTION_USER_NOT_AUTHENTICATED);
        }

        dto.setUpdatedBy(user.getSub());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setElementStatus(ElementStatus.NEW);
        dto.setStatus(Status.ACTIVE);
        return dto;
    }

    public BasicAuditDTO getAuditAttributesForSystem(BasicAuditDTO dto) {
        dto.setCreatedBy("SYSTEM");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setElementStatus(ElementStatus.NEW);
        dto.setStatus(Status.ACTIVE);
        return dto;
    }

    private final UserDetailsServiceImpl userDetailsService;
}