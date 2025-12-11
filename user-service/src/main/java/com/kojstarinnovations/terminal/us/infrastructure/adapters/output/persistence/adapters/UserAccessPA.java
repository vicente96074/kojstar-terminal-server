package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserAccessResponse;
import com.kojstarinnovations.terminal.us.domain.model.UserAccessDTO;
import com.kojstarinnovations.terminal.us.domain.model.UserAccessIDDTO;
import com.kojstarinnovations.terminal.us.domain.opextends.UserAccessOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserAccessId;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.UserAccessPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * UserAccessPA - Persistence Adapter for UserAccess
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserAccessPA implements UserAccessOP {

    /**
     * Method to save a modelDto
     *
     * @param dto the modelDto to be saved
     * @return modelDto
     */
    @Override
    public UserAccessResponse save(UserAccessDTO dto) {
        return mapper.entityToResponse(
                repository.save(
                        mapper.dtoToEntity(dto)
                )
        );
    }

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    @Override
    public Optional<UserAccessResponse> getById(UserAccessIDDTO id) {
        return repository.findById(UserAccessId.builder()
                        .accessId(id.getAccessId())
                        .userId(id.getUserId()).build())
                .map(mapper::entityToResponse);
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<UserAccessResponse> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToResponse);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<UserAccessResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    /**
     * Method to update a modelDto by id
     *
     * @param dto the modelDto to be updated
     * @param id  the id of the modelDto to be updated
     * @return modelDto updated
     */
    @Override
    public UserAccessResponse updateById(UserAccessDTO dto, UserAccessIDDTO id) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(UserAccessIDDTO id) {
        this.repository.deleteById(UserAccessId.builder()
                .accessId(id.getAccessId())
                .userId(id.getUserId()).build());
    }

    /**
     * existsById method
     *
     * @param id id to search
     * @return true if the userAccess exists, false otherwise
     */
    @Override
    public boolean existsById(UserAccessIDDTO id) {
        return repository.existsById(UserAccessId.builder()
                .accessId(id.getAccessId())
                .userId(id.getUserId()).build());
    }

    /**
     * Delete User Access by User Id
     *
     * @param userId User Id
     */
    @Override
    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }

    private final UserAccessRepository repository;
    private final UserAccessPM mapper;
}