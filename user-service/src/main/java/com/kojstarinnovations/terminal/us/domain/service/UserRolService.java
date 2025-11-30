package com.kojstarinnovations.terminal.us.domain.service;

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
        boolean exists = persistenceAdapter.existsById(userRolIdDto);

        return exists;
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
            throw new DuplicateException("UserRol already exists");
        }
        UserRolDTO dto = domainMapper.requestToDTO(request);
        dto = (UserRolDTO) attributeUSService.getAuditAttributesForSystem(dto); // Add audit attributes for system
        dto = persistenceAdapter.save(dto);

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * deleteById method
     *
     * @param id id to delete
     */
    @Override
    public void deleteById(UserRolIdRequest id) {
        UserRolIDDTO userRolIdDto = new UserRolIDDTO(id.getUserId(), id.getRolId());
        persistenceAdapter.deleteById(userRolIdDto);
    }

    /**
     * Deletes all user rols by user id
     *
     * @param userId the user id
     */
    @Override
    public void deleteByUserId(String userId) {
        persistenceAdapter.deleteByUserId(userId);
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
        Optional<UserRolDTO> optionalUserRolDto = persistenceAdapter.getById(userRolIdDto);

        return domainMapper.dtoToResponse(optionalUserRolDto.orElseThrow(() -> new NotFoundException("UserRol not found")));
    }

    /**
     * Get all objects
     *
     * @param pageable the pageable object
     * @return Page<Response>
     */
    @Override
    public Page<UserRolResponse> getPage(Pageable pageable) {
        Page<UserRolResponse> responses = Optional.of(persistenceAdapter.getPage(pageable))
                .filter(page -> !page.isEmpty())
                .orElseThrow(() -> new NotFoundException("UserRol not found by pageable"))
                .map(domainMapper::dtoToResponse);

        return responses;
    }

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    @Override
    public List<UserRolResponse> getAll() {
        List<UserRolResponse> responses = Optional.of(persistenceAdapter.getAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException("UserRol not found"))
                .stream()
                .map(domainMapper::dtoToResponse)
                .toList();

        return responses;
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

        dto = persistenceAdapter.updateById(dto, new UserRolIDDTO(id.getUserId(), id.getRolId()));

        return domainMapper.dtoToResponse(dto);
    }

    private final AuditAttributeUSService attributeUSService;
    private final UserRolPA persistenceAdapter;
    private final UserRolDM domainMapper;
}