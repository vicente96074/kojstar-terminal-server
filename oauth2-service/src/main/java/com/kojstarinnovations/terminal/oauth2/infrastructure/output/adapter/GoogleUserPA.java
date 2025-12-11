package com.kojstarinnovations.terminal.oauth2.infrastructure.output.adapter;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.payload.oauth2userservice.GoogleUserResponse;
import com.kojstarinnovations.terminal.oauth2.domain.dto.GoogleUserDTO;
import com.kojstarinnovations.terminal.oauth2.domain.opextends.GoogleUserOP;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.mapper.GoogleUserPM;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository.GoogleUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoogleUserPA implements GoogleUserOP {

    /**
     * Method to save a modelDto
     *
     * @param googleUserDTO the modelDto to be saved
     * @return modelDto
     */
    @Override
    public GoogleUserResponse save(GoogleUserDTO googleUserDTO) {
        return mapper.entityToResponse(
                repository.save(mapper.dtoToEntity(googleUserDTO))
        );
    }

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    @Override
    public Optional<GoogleUserResponse> getById(String id) {
        return repository.findById(id)
                .map(mapper::entityToResponse);
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<GoogleUserResponse> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToResponse);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<GoogleUserResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    /**
     * Method to update a modelDto by id
     *
     * @param googleUserDTO the modelDto to be updated
     * @param id            the id of the modelDto to be updated
     * @return modelDto updated
     */
    @Override
    public GoogleUserResponse updateById(GoogleUserDTO googleUserDTO, String id) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param s the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * Method to check if a modelDto exists by id
     *
     * @param id the id of the modelDto to be checked
     * @return true if the modelDto exists, false otherwise
     */
    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<GoogleUserResponse> getByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::entityToResponseWithAuthorities);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private final GoogleUserRepository repository;
    private final GoogleUserPM mapper;
}
