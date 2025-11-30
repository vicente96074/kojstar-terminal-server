package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.transport.userservice.RolRequest;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.us.domain.dmimpl.RolDM;
import com.kojstarinnovations.terminal.us.domain.opextends.RolOP;
import com.kojstarinnovations.terminal.us.domain.ucextends.RolUC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * RolService - Implementation of the Rol use case interface for saving, deleting, retrieving, and updating Rol points.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RolService implements RolUC {

    /**
     * existsById method
     *
     * @param id id of the object to be retrieved
     * @return true if the object exists, false otherwise
     */
    @Override
    public boolean existsById(String id) {
        boolean exists = outputPort.existsById(id);

        return exists;
    }

    /**
     * existsByRolName method
     *
     * @param rolName rolName to search
     * @return true if the rol exists, false otherwise
     */
    @Override
    public boolean existsByRolName(RolName rolName) {
        boolean exists = outputPort.existsByRolName(rolName);

        return exists;
    }

    /**
     * save method
     *
     * @param request request to save
     * @return the saved request
     */
    @Override
    public RolResponse save(RolRequest request) {

        if (existsByRolName(request.getRolName())) {
            throw new DuplicateException("Rol already exists");
        }

        RolDTO dto = domainMapper.requestToDTO(request);
        dto.setId(null);
        dto.setStatus(Status.ACTIVE);

        dto = outputPort.save(dto);

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * deleteById method
     *
     * @param id id of the object to be deleted
     */
    @Override
    public void deleteById(String id) {
        outputPort.deleteById(id);
    }

    /**
     * getById method
     *
     * @param id id of the object to be retrieved
     * @return the request found
     */
    @Override
    public RolResponse getById(String id) {
        Optional<RolDTO> optionalRolDto = outputPort.getById(id);

        return domainMapper.dtoToResponse(optionalRolDto.orElseThrow(() -> new NotFoundException("Rol not found by ID")));
    }

    /**
     * Get all objects
     *
     * @param pageable the pageable object
     * @return Page<Response>
     */
    @Override
    public Page<RolResponse> getPage(Pageable pageable) {
        Page<RolResponse> responses = Optional.of(outputPort.getPage(pageable)).filter(page -> !page.isEmpty()).orElseThrow(() -> new NotFoundException("Rol not found by pageable")).map(domainMapper::dtoToResponse);

        return responses;
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<RolResponse> getAll() {
        List<RolResponse> responses = Optional.of(outputPort.getAll()).filter(list -> !list.isEmpty()).orElseThrow(() -> new NotFoundException("All roles not found")).stream().map(domainMapper::dtoToResponse).toList();

        return responses;
    }

    /**
     * updateById method
     *
     * @param request request to be updated
     * @param id      id of the object to be updated
     * @return the updated request
     */
    @Override
    public RolResponse updateById(RolRequest request, String id) {

        if (!existsById(id)) {
            throw new NotFoundException("Rol not found by ID");
        }

        RolDTO modelDto = domainMapper.requestToDTO(request);
        modelDto.setStatus(Status.ACTIVE);

        modelDto = outputPort.updateById(modelDto, id);

        return domainMapper.dtoToResponse(modelDto);
    }

    /**
     * getByRolName method
     *
     * @param rolName rolName to search
     * @return the request found
     */
    @Override
    public RolResponse getByRolName(RolName rolName) {
        Optional<RolDTO> optionalRolDto = outputPort.getByRolName(rolName);

        return domainMapper.dtoToResponse(optionalRolDto.orElseThrow(() -> new NotFoundException("Rol not found by rolName")));
    }

    private final RolOP outputPort;
    private final RolDM domainMapper;
}