package com.kojstarinnovations.terminal.st.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nStoreConstants;
import com.kojstarinnovations.terminal.commons.data.payload.storeservice.FiscalDirectionResponse;
import com.kojstarinnovations.terminal.commons.data.transport.storeservice.FiscalDirectionRequest;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.st.domain.dmimpl.FiscalDirectionDM;
import com.kojstarinnovations.terminal.st.domain.model.FiscalDirectionDTO;
import com.kojstarinnovations.terminal.st.domain.opextends.FiscalDirectionOP;
import com.kojstarinnovations.terminal.st.domain.ucextends.FiscalDirectionUC;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FiscalDirectionService implements FiscalDirectionUC {

    /**
     * Use case to save a request with transactional support
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public FiscalDirectionResponse save(FiscalDirectionRequest request) {
        FiscalDirectionDTO dto = mapper.requestToDTO(request);
        dto = (FiscalDirectionDTO) auditAttributeService.getAuditAttributesForSystem(dto);
        FiscalDirectionResponse payload = outputPort.save(dto);
        log.info("Fiscal direction created successfully with id:{} by user with id:{}", payload.getId(), userDetailsService.getUserFromAuth().getSub());
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
        log.info("Fiscal direction deleted successfully with:{} by user with id: {}", id, userDetailsService.getUserFromAuth().getSub());
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
    public FiscalDirectionResponse getById(String id) {
        FiscalDirectionResponse payload = outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException(I18nStoreConstants.EXCEPTION_FISCAL_DIRECTION_NOT_FOUND_BY_ID));
        log.info("Fiscal direction retrieved with id: {} by user with id: {}", id, userDetailsService.getUserFromAuth().getSub());
        return payload;
    }

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<FiscalDirectionResponse> getPage(Pageable pageable) {
        Page<FiscalDirectionResponse> payload = Optional.of(outputPort.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nStoreConstants.EXCEPTION_FISCAL_DIRECTION_PAGE_NOT_FOUND));
        log.info("Page of fiscal direction retrieved successfully by user with id: {}", userDetailsService.getUserFromAuth().getSub());
        return payload;
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<FiscalDirectionResponse> getAll() {
        List<FiscalDirectionResponse> payload = Optional.of(outputPort.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nStoreConstants.EXCEPTION_FISCAL_DIRECTION_ALL_NOT_FOUND));
        log.info("All fiscal directions retrieved successfully by user with id: {}", userDetailsService.getUserFromAuth().getSub());
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
    public FiscalDirectionResponse updateById(FiscalDirectionRequest request, String id) {
        FiscalDirectionDTO dto = mapper.requestToDTO(request);
        dto = (FiscalDirectionDTO) auditAttributeService.getAuditAttributesForUpdate(dto);
        FiscalDirectionResponse payload = outputPort.updateById(dto, id);
        log.info("Fiscal direction with id: {} updated successfully by user with id: {}", id, userDetailsService.getUserFromAuth().getSub());

        return payload;
    }

    private final FiscalDirectionDM mapper;
    private final FiscalDirectionOP outputPort;
    private final AuditAttributeService auditAttributeService;
    private final UserDetailsServiceImpl userDetailsService;
}