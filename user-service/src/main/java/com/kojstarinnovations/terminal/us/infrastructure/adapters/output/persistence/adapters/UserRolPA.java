package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserRolResponse;
import com.kojstarinnovations.terminal.us.domain.model.UserRolDTO;
import com.kojstarinnovations.terminal.us.domain.model.UserRolIDDTO;
import com.kojstarinnovations.terminal.us.domain.opextends.UserRolOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.UserRolId;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.UserRolPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.UserRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * UserRolPA - Persistence Adapter for UserRol
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserRolPA implements UserRolOP {

    /**
     * Check if entity exists by UserRol ID
     *
     * @param id the UserRol ID
     * @return boolean
     */
    @Override
    public boolean existsById(UserRolIDDTO id) {
        return repository.existsById(
                UserRolId.builder()
                        .rolId(id.getRolId())
                        .userId(id.getUserId())
                        .build()
        );
    }

    /**
     * Saves a user and his/her rol to the relational table
     *
     * @param dto the model to be saved
     * @return the saved model
     */
    @Override
    public UserRolResponse save(UserRolDTO dto) {
        return mapper.entityToResponse(
                repository.save(
                        mapper.dtoToEntity(dto)
                )
        );
    }

    /**
     * Get by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return the modelDto retrieved
     */
    @Override
    public Optional<UserRolResponse> getById(UserRolIDDTO id) {
        return repository.findById(
                        UserRolId.builder()
                                .rolId(id.getRolId())
                                .userId(id.getUserId())
                                .build()
                )
                .map(mapper::entityToResponse);
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<UserRolResponse> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToResponse);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<UserRolResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    /**
     * Updates a modelDto by id
     *
     * @param dto the modelDto to be updated
     * @param id  the id of the modelDto to be updated
     * @return the updated modelDto
     */
    @Override
    public UserRolResponse updateById(UserRolDTO dto, UserRolIDDTO id) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * Deletes a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(UserRolIDDTO id) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * Delete User Rol by User Id
     *
     * @param userId User Id
     */
    @Override
    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }

    private final UserRolPM mapper;
    private final UserRolRepository repository;
}