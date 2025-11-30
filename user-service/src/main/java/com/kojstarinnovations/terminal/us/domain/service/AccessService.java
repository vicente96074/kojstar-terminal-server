package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
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
            throw new DuplicateException(ExceptionConstants.DUPLICATE_ACCESS);
        }

        AccessDTO dto = domainMapper.requestToDTO(request);
        dto.setId(null);
        dto.setStatus(Status.ACTIVE);
        dto = outputPort.save(dto);

        log.info("An access was saved for users and the ID was obtained: {}", dto.getId());

        return domainMapper.dtoToResponse(dto);
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
        Optional<AccessDTO> optionalAccessDTO = outputPort.getById(id);
        log.info("An access was retrieved for users and the ID was retrieved: {}", id);
        return domainMapper.dtoToResponse(optionalAccessDTO.orElseThrow(() -> new NotFoundException("Access not found by ID")));
    }

    /**
     * Get all objects
     *
     * @param pageable the pageable object
     * @return Page<Response>
     */
    @Override
    public Page<AccessResponse> getPage(Pageable pageable) {
        Page<AccessResponse> responses = Optional.of(outputPort.getPage(pageable)).filter(page -> !page.isEmpty()).orElseThrow(() -> new NotFoundException(ExceptionConstants.PAGE_ACCESS_NOT_FOUND)).map(domainMapper::dtoToResponse);

        log.info("An access was retrieved for users and the pageable: {}", pageable);

        return responses;
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<AccessResponse> getAll() {
        List<AccessResponse> responses = Optional.of(outputPort.getAll()).filter(list -> !list.isEmpty()).orElseThrow(() -> new NotFoundException(ExceptionConstants.LIST_ACCESS_NOT_FOUND)).stream().map(domainMapper::dtoToResponse).toList();

        log.info("All accesses were retrieved for users.");

        return responses;
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
            throw new NotFoundException(ExceptionConstants.ACCESS_NOT_FOUND);
        }

        AccessDTO modelDto = domainMapper.requestToDTO(request);
        modelDto.setStatus(Status.ACTIVE);

        modelDto = outputPort.updateById(modelDto, id);

        log.info("An access was updated for users and the ID was updated: {}", id);

        return domainMapper.dtoToResponse(modelDto);
    }

    /**
     * getByAccessName
     *
     * @param accessName the access name to search
     * @return access response search result
     */
    @Override
    public AccessResponse getByAccessName(AccessName accessName) {
        Optional<AccessDTO> optionalAccessDTO = outputPort.getByAccessName(accessName);

        log.info("An access was retrieved for users and the accessName was retrieved: {}", accessName);

        return domainMapper.dtoToResponse(optionalAccessDTO.orElseThrow(() -> new NotFoundException(ExceptionConstants.ACCESS_NOT_FOUND)));
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