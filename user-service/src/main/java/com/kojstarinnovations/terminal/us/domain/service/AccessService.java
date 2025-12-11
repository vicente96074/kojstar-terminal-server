package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nUserConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.AccessRequest;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.us.domain.dmimpl.AccessDM;
import com.kojstarinnovations.terminal.us.domain.opextends.AccessOP;
import com.kojstarinnovations.terminal.us.domain.ucextends.AccessUC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * AccessService - Implementation of the Access use case interface for saving, deleting, retrieving, and updating Access points.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccessService implements AccessUC {

    /**
     * Save entity
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public AccessResponse save(AccessRequest request) {

        if (existsByAccessName(request.getAccessName())) {
            throw new DuplicateException(I18nUserConstants.EXCEPTION_ACCESS_DUPLICATE);
        }

        AccessDTO dto = domainMapper.requestToDTO(request);
        dto.setId(null);
        dto.setStatus(Status.ACTIVE);
        return outputPort.save(dto);
    }

    /**
     * Delete entity by id
     *
     * @param id the id of the entity to be deleted
     */
    @Override
    public void deleteById(String id) {
        outputPort.deleteById(id);
        log.info("An access was deleted for users and the ID was deleted: {}", id);
    }

    /**
     * Get all objects
     *
     * @param id id of the object to be retrieved
     * @return QueryResponse
     */
    @Override
    public AccessResponse getById(String id) {
        return outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_ACCESS_NOT_FOUND_BY_ID));
    }

    /**
     * Get all objects
     *
     * @param pageable the pageable object
     * @return Page<Response>
     */
    @Override
    public Page<AccessResponse> getPage(Pageable pageable) {
        return Optional.of(outputPort.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_ACCESS_PAGE_NOT_FOUND));
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<AccessResponse> getAll() {
        return Optional.of(outputPort.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_ACCESS_LIST_NOT_FOUND));
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id      id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public AccessResponse updateById(AccessRequest request, String id) {
        if (!existsById(id)) {
            throw new NotFoundException(I18nUserConstants.EXCEPTION_ACCESS_NOT_FOUND_BY_ID);
        }

        AccessDTO modelDto = domainMapper.requestToDTO(request);
        modelDto.setStatus(Status.ACTIVE);

        return outputPort.updateById(modelDto, id);
    }

    /**
     * getByAccessName
     *
     * @param accessName the access name to search
     * @return access response search result
     */
    @Override
    public AccessResponse getByAccessName(AccessName accessName) {
        return outputPort.getByAccessName(accessName)
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_ACCESS_NOT_FOUND_BY_NAME));
    }

    /**
     * existsByAccessName
     *
     * @param accessName the access name to search
     * @return true if the access exists, false otherwise
     */
    @Override
    public boolean existsByAccessName(AccessName accessName) {
        boolean exists = outputPort.existsByAccessName(accessName);

        log.info("An access existence was checked for users and the accessName was checked: {}", accessName);

        return exists;
    }

    /**
     * existsById
     *
     * @param id the id to search
     * @return true if the access exists, false otherwise
     */
    @Override
    public boolean existsById(String id) {
        boolean exists = outputPort.existsById(id);

        log.info("An access existence was checked for users and the ID was checked: {}", id);

        return exists;
    }

    private final AccessOP outputPort;
    private final AccessDM domainMapper;
}