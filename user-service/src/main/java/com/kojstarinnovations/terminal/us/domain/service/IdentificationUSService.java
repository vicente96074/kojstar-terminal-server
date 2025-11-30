package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.IdentificationUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.IdentificationUSResponse;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.us.application.data.request.IdentificationUSRequest;
import com.kojstarinnovations.terminal.us.domain.dmimpl.IdentificationUSDM;
import com.kojstarinnovations.terminal.us.domain.opextends.IdentificationUSOP;
import com.kojstarinnovations.terminal.us.domain.ucextends.IdentificationUSUC;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * IdentificationUSService - Implementation of the Identification use case interface for saving, deleting, retrieving, and updating Identification points.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IdentificationUSService implements IdentificationUSUC {

    /**
     * Save a request
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public IdentificationUSResponse save(IdentificationUSRequest request) {

        if (existsByIDNumberAndIDTypeAndNationality(
                request.getIdentificationNumber(),
                request.getIdentificationType(),
                request.getNationalityCode())
        ) {
            throw new DuplicateException("Este número de identificación ya existe");
        }

        IdentificationUSDTO dto = domainMapper.requestToDTO(request);
        dto.setId(null);
        dto.setStatus(Status.ACTIVE);
        dto = outputPort.save(dto);

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
     * Get all objects
     *
     * @param id id of the object to be retrieved
     * @return QueryResponse
     */
    @Override
    public IdentificationUSResponse getById(String id) {
        IdentificationUSDTO dto = outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException("Identification not found by ID"));

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * Get all objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<IdentificationUSResponse> getPage(Pageable pageable) {
        Page<IdentificationUSResponse> responses = Optional.of(outputPort.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException("Identification not found by pageable"))
                .map(domainMapper::dtoToResponse);

        return responses;
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<IdentificationUSResponse> getAll() {
        List<IdentificationUSResponse> responses = Optional.of(outputPort.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException("Identification not found"))
                .stream()
                .map(domainMapper::dtoToResponse)
                .toList();

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
    public IdentificationUSResponse updateById(IdentificationUSRequest request, String id) {
        IdentificationUSDTO dto = domainMapper.requestToDTO(request);
        dto = outputPort.updateById(dto, id);

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * Check if entity exists by identification number and identification type
     *
     * @param idNumber    the identification number
     * @param idType      the identification type
     * @param nationality the nationality
     * @return boolean
     */
    @Override
    public boolean existsByIDNumberAndIDTypeAndNationality(
            String idNumber,
            String idType,
            String nationality
    ) {

        return outputPort.existsByIDNumberAndIDTypeAndNationality(
                idNumber,
                IdentificationType.valueOf(idType),
                CountryCodeISO.valueOf(nationality)
        );
    }

    private final IdentificationUSOP outputPort;
    private final IdentificationUSDM domainMapper;
}