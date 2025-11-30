package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import com.kojstarinnovations.terminal.commons.data.enums.ElementStatus;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.exception.UserNotFoundException;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * AuditAttributeUSService - Service that handles the audit attributes for the User Service.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuditAttributeUSService {

    /**
     * Get audit attributes for system
     *
     * @param dto the AuditAttributeUSDTO
     * @return AuditAttributeUSDTO
     */
    public BasicAuditDTO getAuditAttributesForSystem(BasicAuditDTO dto) {
        dto.setCreatedBy("SYSTEM");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedBy("SYSTEM");
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setElementStatus(ElementStatus.NEW);
        dto.setStatus(Status.ACTIVE);

        return dto;
    }

    /**
     * Get audit attributes for update
     *
     * @param dto the AuditAttributeUSDTO
     * @return AuditAttributeUSDTO
     */
    public BasicAuditDTO getAuditAttributesForUpdate(BasicAuditDTO dto) {
        PrincipalUser principalUser = userDetailsService.getUserFromAuth();

        if (principalUser == null) {
            throw new UserNotFoundException("No user found in the security context");
        }

        dto.setUpdatedAt(LocalDateTime.now());
        dto.setElementStatus(ElementStatus.UPDATED);
        dto.setStatus(Status.ACTIVE);

        return dto;
    }

    private final UserDetailsServiceImpl userDetailsService;
}