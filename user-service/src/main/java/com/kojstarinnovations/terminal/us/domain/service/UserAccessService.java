package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserAccessResponse;
import com.kojstarinnovations.terminal.us.application.data.request.UserAccessRequest;
import com.kojstarinnovations.terminal.us.domain.dmimpl.UserAccessDM;
import com.kojstarinnovations.terminal.us.domain.model.UserAccessDTO;
import com.kojstarinnovations.terminal.us.domain.opextends.UserAccessOP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserAccessService - Implementation of the User Access use case interface for saving, deleting, retrieving, and updating User Access points.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAccessService {

    /**
     * Save entity
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    public UserAccessResponse save(UserAccessRequest request) {
        UserAccessDTO dto = domainMapper.requestToDTO(request);
        dto = (UserAccessDTO) auditAttributeUSService.getAuditAttributesForSystem(dto); // Add audit attributes for system

        dto = outputPort.save(dto);

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * Delete entity by id
     *
     * @param userId the id of the entity to be deleted
     */
    public void deleteByUserId(String userId) {
        outputPort.deleteByUserId(userId);
    }

    private final AuditAttributeUSService auditAttributeUSService;
    private final UserAccessOP outputPort;
    private final UserAccessDM domainMapper;
}