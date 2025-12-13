package com.kojstarinnovations.terminal.st.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nStoreConstants;
import com.kojstarinnovations.terminal.commons.data.payload.storeservice.BranchOfficeResponse;
import com.kojstarinnovations.terminal.commons.data.transport.storeservice.BranchOfficeRequest;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.st.domain.dmimpl.BranchOfficeDM;
import com.kojstarinnovations.terminal.st.domain.model.BranchOfficeDTO;
import com.kojstarinnovations.terminal.st.domain.opextends.BranchOfficeOP;
import com.kojstarinnovations.terminal.st.domain.ucextends.BranchOfficeUC;
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
public class BranchOfficeService implements BranchOfficeUC {

    /**
     * Use case to save a request with transactional support
     *
     * @param branchOfficeRequest the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public BranchOfficeResponse save(BranchOfficeRequest branchOfficeRequest) {
        return null;
    }

    /**
     * Delete an entity by its id with transactional support
     *
     * @param id the id of the entity to be deleted
     */
    @Override
    public void deleteById(String id) {

    }

    /**
     * Check if an object exists by its id
     *
     * @param id id of the object to be retrieved
     * @return boolean
     */
    @Override
    public boolean existsById(String id) {
        return false;
    }

    /**
     * Get object by id
     *
     * @param id id of the object to be retrieved
     * @return QueryResponse
     */
    @Override
    public BranchOfficeResponse getById(String id) {
        BranchOfficeResponse payload = outputPort.getById(id)
                .orElseThrow(() -> new NotFoundException(I18nStoreConstants.EXCEPTION_BRANCH_OFFICE_NOT_FOUND_BY_ID));
        return payload;
    }

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    @Override
    public Page<BranchOfficeResponse> getPage(Pageable pageable) {
        Page<BranchOfficeResponse> payload = Optional.of(outputPort.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nStoreConstants.EXCEPTION_BRANCH_OFFICE_PAGE_NOT_FOUND));
        log.info("Page of branches retrieved successfully by user with id: {}", userDetailsService.getUserFromAuth().getSub());

        return payload;
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<BranchOfficeResponse> getAll() {
        List<BranchOfficeResponse> payload = Optional.of(outputPort.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nStoreConstants.EXCEPTION_BRANCH_OFFICE_ALL_NOT_FOUND));
        log.info("All branches retrieved successfully by user with id: {}", userDetailsService.getUserFromAuth().getSub());

        return payload;
    }

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id                  id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public BranchOfficeResponse updateById(BranchOfficeRequest request, String id) {
        BranchOfficeDTO dto = mapper.requestToDTO(request);
        dto = (BranchOfficeDTO) auditAttributeService.getAuditAttributesForNew(dto);
        BranchOfficeResponse payload = outputPort.updateById(dto, id);
        log.info("Branch office updated successfully with id: {} by user with id: {}", id, userDetailsService.getUserFromAuth().getSub());
        return payload;
    }

    private final BranchOfficeOP outputPort;
    private final BranchOfficeDM mapper;
    private final AuditAttributeService auditAttributeService;
    private final UserDetailsServiceImpl userDetailsService;
}
