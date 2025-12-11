package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nUserConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.AddressUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AddressUSResponse;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.us.application.data.request.AddressUSRequest;
import com.kojstarinnovations.terminal.us.domain.dmimpl.AddressUSDM;
import com.kojstarinnovations.terminal.us.domain.opextends.AddressUSOP;
import com.kojstarinnovations.terminal.us.domain.ucextends.AddressUSUC;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * AddressUSService - Implementation of the Address users use case interface for saving, deleting, retrieving, and updating Address points.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddressUSService implements AddressUSUC {

    /**
     * Save a request
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public AddressUSResponse save(AddressUSRequest request) {

        AddressUSDTO dto = domainMapper.requestToDTO(request);
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
        log.info("An address was deleted for users and the ID was deleted: {}", id);
    }

    /**
     * Check if entity exists by id
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
    public AddressUSResponse getById(String id) {
        return outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_ADDRESS_NOT_FOUND_BY_ID));
    }

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<AddressUSResponse> getPage(Pageable pageable) {
        return Optional.of(outputPort.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_ADDRESS_PAGE_NOT_FOUND));
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<AddressUSResponse> getAll() {
        return Optional.of(outputPort.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_ADDRESS_LIST_EMPTY));
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id      id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public AddressUSResponse updateById(AddressUSRequest request, String id) {
        AddressUSDTO dto = domainMapper.requestToDTO(request);
        dto.setId(id);
        return outputPort.updateById(dto, id);
    }

    private final AddressUSOP outputPort;
    private final AddressUSDM domainMapper;
}