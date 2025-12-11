package com.kojstarinnovations.terminal.us.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nUserConstants;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserRolResponse;
import com.kojstarinnovations.terminal.commons.exception.DuplicateException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.us.application.data.request.UserRolIdRequest;
import com.kojstarinnovations.terminal.us.application.data.request.UserRolRequest;
import com.kojstarinnovations.terminal.us.domain.dmimpl.UserRolDM;
import com.kojstarinnovations.terminal.us.domain.model.UserRolDTO;
import com.kojstarinnovations.terminal.us.domain.model.UserRolIDDTO;
import com.kojstarinnovations.terminal.us.domain.ucextends.UserRolUC;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters.UserRolPA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * UserRolService - Implementation of the UserRol use case interface for saving, deleting, retrieving, and updating UserRol points.
 * Each service also includes a Transactional Service to manage data integrity in the database.
 * Every transaction is audited and saved along with the entity information.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserRolService implements UserRolUC {

    /**
     * existsById method
     *
     * @param userRolIdRequest userRolIdRequest to search
     * @return true if the userRol exists, false otherwise
     */
    @Override
    public boolean existsById(UserRolIdRequest userRolIdRequest) {
        UserRolIDDTO userRolIdDto = new UserRolIDDTO(userRolIdRequest.getUserId(), userRolIdRequest.getRolId());
        return outputPort.existsById(userRolIdDto);
    }

    /**
     * save method
     *
     * @param request request to save
     * @return the saved request
     */
    @Override
    public UserRolResponse save(UserRolRequest request) {
        if (existsById(new UserRolIdRequest(request.getUserId(), request.getRolId()))) {
            throw new DuplicateException(I18nUserConstants.EXCEPTION_USER_ROL_ALREADY_EXISTS);
        }
        UserRolDTO dto = domainMapper.requestToDTO(request);
        dto = (UserRolDTO) attributeUSService.getAuditAttributesForSystem(dto); // Add audit attributes for system
        return outputPort.save(dto);
    }

    /**
     * deleteById method
     *
     * @param id id to delete
     */
    @Override
    public void deleteById(UserRolIdRequest id) {
        UserRolIDDTO userRolIdDto = new UserRolIDDTO(id.getUserId(), id.getRolId());
        outputPort.deleteById(userRolIdDto);
    }

    /**
     * Deletes all user roles by user id
     *
     * @param userId the user id
     */
    @Override
    public void deleteByUserId(String userId) {
        outputPort.deleteByUserId(userId);
    }

    /**
     * getById method
     *
     * @param ID id to search
     * @return the userRol retrieved
     */
    @Override
    public UserRolResponse getById(UserRolIdRequest ID) {
        UserRolIDDTO userRolIdDto = new UserRolIDDTO(ID.getUserId(), ID.getRolId());
        return outputPort.getById(userRolIdDto)
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_USER_ROL_NOT_FOUND_BY_ID));
    }

    /**
     * Get all objects
     *
     * @param pageable the pageable object
     * @return Page<Response>
     */
    @Override
    public Page<UserRolResponse> getPage(Pageable pageable) {
        return Optional.of(outputPort.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_USER_ROL_PAGE_NOT_FOUND));
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<UserRolResponse> getAll() {
        return Optional.of(outputPort.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(I18nUserConstants.EXCEPTION_USER_ROL_ALL_NOT_FOUND));
    }

    /**
     * updateById method
     *
     * @param request request to update
     * @param id      id to update
     * @return the updated userRol
     */
    @Override
    public UserRolResponse updateById(UserRolRequest request, UserRolIdRequest id) {

        if (!existsById(id)) {
            throw new NotFoundException("UserRol not found");
        }

        UserRolDTO dto = domainMapper.requestToDTO(request);
        dto.setStatus(Status.ACTIVE);

        return outputPort.updateById(dto, new UserRolIDDTO(id.getUserId(), id.getRolId()));
    }

    private final AuditAttributeUSService attributeUSService;
    private final UserRolPA outputPort;
    private final UserRolDM domainMapper;
}