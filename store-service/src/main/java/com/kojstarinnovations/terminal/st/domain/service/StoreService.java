package com.kojstarinnovations.terminal.st.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nStoreConstants;
import com.kojstarinnovations.terminal.commons.data.constants.I18nUserConstants;
import com.kojstarinnovations.terminal.commons.data.payload.storeservice.StoreResponse;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.commons.exception.UnauthorizedException;
import com.kojstarinnovations.terminal.st.application.data.request.StoreRequest;
import com.kojstarinnovations.terminal.st.domain.dmimpl.StoreDM;
import com.kojstarinnovations.terminal.st.domain.model.StoreDTO;
import com.kojstarinnovations.terminal.st.domain.opextends.StoreOP;
import com.kojstarinnovations.terminal.st.domain.ucextends.StoreUC;
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
public class StoreService implements StoreUC {

    /**
     * Use case to save a request with transactional support
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public StoreResponse save(StoreRequest request) {
        StoreDTO dto = mapper.requestToDTO(request);
        dto.setId(null);
        dto = (StoreDTO) auditAttributeService.getAuditAttributesForNew(dto);

        StoreResponse payload = outputPort.save(dto);
        log.info("Store created successfully with id={} by user with id {}", payload.getId(), userDetailsService.getUserFromAuth().getSub());
        return payload;
    }

    /**
     * Delete an entity by its id with transactional support
     *
     * @param id the id of the entity to be deleted
     */
    @Override
    public void deleteById(String id) {
        outputPort.deleteById(id);
        log.info("Store with id {} deleted successfully by user with id {}", id, userDetailsService.getUserFromAuth().getSub());
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
    public StoreResponse getById(String id) {
        StoreResponse payload = outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException(I18nStoreConstants.EXCEPTION_STORE_NOT_FOUND_BY_ID));
        log.info("Store with id {} retrieved by user with id {}", id, userDetailsService.getUserFromAuth().getSub());
        return payload;
    }

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<StoreResponse> getPage(Pageable pageable) {
        Page<StoreResponse> payload = outputPort.getPage(pageable);
        log.info("Return page store and retrieved by user with id {}", userDetailsService.getUserFromAuth().getSub());
        return payload;
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<StoreResponse> getAll() {
        List<StoreResponse> payload = outputPort.getAll();
        log.info("Return all stores and retrieved by user with id {}", userDetailsService.getUserFromAuth().getSub());
        return payload;
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id      id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public StoreResponse updateById(StoreRequest request, String id) {
        StoreDTO dto = mapper.requestToDTO(request);
        dto = (StoreDTO) auditAttributeService.getAuditAttributesForUpdate(dto);

        StoreResponse payload = outputPort.updateById(dto, id);

        log.info("Store with id {} updated successfully by user with id {}", id, dto.getUpdatedBy());

        return payload;
    }

    @Override
    public StoreResponse execute(StoreRequest request) {
        StoreDTO dto = mapper.requestToDTO(request);
        dto.setId(null);
        dto = (StoreDTO) auditAttributeService.getAuditAttributesForSystem(dto);

        StoreResponse payload = outputPort.save(dto);
        log.info("Store created successfully by system");

        return payload;
        //throw new UnauthorizedException(I18nUserConstants.EXCEPTION_TI_INVALID_TOKEN_FORMAT);
    }

    private final StoreOP outputPort;
    private final StoreDM mapper;
    private final AuditAttributeService auditAttributeService;
    private final UserDetailsServiceImpl userDetailsService;
}