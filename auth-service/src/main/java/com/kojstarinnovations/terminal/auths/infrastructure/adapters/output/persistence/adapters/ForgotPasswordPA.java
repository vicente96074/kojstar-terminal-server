package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.auths.domain.opextends.ForgotPasswordOP;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.pmimpl.ForgotPasswordPM;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.repository.ForgotPasswordRepository;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.ForgotPasswordDTO;
import com.kojstarinnovations.terminal.commons.data.payload.authentication.ForgotPasswordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * ForgotPasswordPA - Persistence Adapter for ForgotPassword
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class ForgotPasswordPA implements ForgotPasswordOP {

    /**
     * Method to save a modelDto
     *
     * @param dto the modelDto to be saved
     * @return modelDto
     */
    @Override
    public ForgotPasswordResponse save(ForgotPasswordDTO dto) {
        return persistenceMapper.entityToResponse(
                repository.save(persistenceMapper.dtoToEntity(dto))
        );
    }

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    @Override
    public Optional<ForgotPasswordResponse> getById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<ForgotPasswordResponse> getPage(Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<ForgotPasswordResponse> getAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to update a modelDto by id
     *
     * @param dto the modelDto to be updated
     * @param id  the id of the modelDto to be updated
     * @return modelDto updated
     */
    @Override
    public ForgotPasswordResponse updateById(ForgotPasswordDTO dto, Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to check if a modelDto exists by id
     *
     * @param id the id of the modelDto to be checked
     * @return true if the modelDto exists, false otherwise
     */
    @Override
    public boolean existsById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to find a modelDto by token
     *
     * @param token the token to be searched
     * @return modelDto with the given token
     */
    @Override
    public Optional<ForgotPasswordResponse> findByToken(String token) {
        return repository.findByToken(token)
                .map(persistenceMapper::entityToResponse);
    }

    /**
     * Method to consume a token
     *
     * @param token the token to be consumed
     */
    @Override
    public void consumeToken(String token) {
        repository.consumeToken(token);
    }

    private final ForgotPasswordPM persistenceMapper;
    private final ForgotPasswordRepository repository;
}
