package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.us.domain.opextends.UserOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.UserPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * UserPA - Persistence Adapter for User
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserPA implements UserOP {

    /**
     * This method validates if a user exists by id
     *
     * @param s the id of the user to be validated
     * @return boolean true if the user exists, false otherwise
     */
    @Override
    public boolean existsById(String s) {
        return repository.existsById(s);
    }

    /**
     * This method saves a user
     *
     * @param dto the user to be saved
     * @return UserDTO the saved user
     */
    @Override
    public UserResponse save(UserDTO dto) {
        return mapper.entityToResponse(
                repository.save(
                        mapper.dtoToEntity(dto)
                )
        );
    }

    /**
     * This method gets a user by id
     *
     * @param id the id of the user to be retrieved
     * @return Optional<UserDTO> the user retrieved
     */
    @Override
    public Optional<UserResponse> getById(String id) {
        return repository.findById(id)
                .map(mapper::entityToResponseWithAccessAndRoles);
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<UserResponse> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToResponseWithAccessAndRoles);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<UserResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToResponseWithAccessAndRoles)
                .toList();
    }

    /**
     * This method updates a user
     *
     * @param dto the user to be updated
     * @param id  the id of the user to be updated
     * @return UserDTO the updated user
     */
    @Override
    public UserResponse updateById(UserDTO dto, String id) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * This method deletes a user by id
     *
     * @param s the id of the user to be deleted
     */
    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * This method validates if a user exists by username
     *
     * @param username the username of the user to be validated
     * @return boolean true if the user exists, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    /**
     * This method validates if a user exists by email
     *
     * @param email the email of the user to be validated
     * @return boolean true if the user exists, false otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    /**
     * This method gets a page of active users by store id
     *
     * @param pageable the pageable object
     * @return Page<UserDTO> the page of active users
     */
    @Override
    public Page<UserResponse> getPageUserActive(Pageable pageable) {
        return repository.getFindByStatus(Status.ACTIVE, pageable)
                .map(mapper::entityToResponseWithAccessAndRoles);
    }

    /**
     * Block user
     *
     * @param userId the user ID
     */
    @Override
    public void blockUser(String userId) {
        repository.updateStatus(userId, Status.BLOCKED);
    }

    /**
     * Get status by user id
     *
     * @param userId the user ID
     * @return Status the status
     */
    @Override
    public Status getStatusById(String userId) {
        return repository.findStatusById(userId);
    }

    private final UserPM mapper;
    private final UserRepository repository;
}