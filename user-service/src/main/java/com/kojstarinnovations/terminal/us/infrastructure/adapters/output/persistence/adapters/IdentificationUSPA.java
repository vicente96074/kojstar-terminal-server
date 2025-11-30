package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.IdentificationUSDTO;
import com.kojstarinnovations.terminal.commons.data.enums.IdentificationType;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.us.domain.opextends.IdentificationUSOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.IdentificationUSPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.IdentificationUSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * IdentificationUSPA - Persistence Adapter for IdentificationUS
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class IdentificationUSPA implements IdentificationUSOP {


    /**
     * Method to save a modelDto
     *
     * @param dto the modelDto to be saved
     * @return modelDto
     */
    @Override
    public IdentificationUSDTO save(IdentificationUSDTO dto) {
        return persistenceMapper.entityToDTO(
                repository.save(
                        persistenceMapper.dtoToEntity(dto)
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
    public Optional<IdentificationUSDTO> getById(String id) {
        return repository.findById(id)
                .map(persistenceMapper::entityToDTO);
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<IdentificationUSDTO> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(persistenceMapper::entityToDTO);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<IdentificationUSDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(persistenceMapper::entityToDTO)
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
    public IdentificationUSDTO updateById(IdentificationUSDTO dto, String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
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

    /**
     * Check if entity exists by identification number and identification type
     *
     * @param idNumber    the identification number
     * @param idType      the identification type
     * @param nationality the nationality
     * @return boolean
     */
    @Override
    public boolean existsByIDNumberAndIDTypeAndNationality(String idNumber, IdentificationType idType, CountryCodeISO nationality) {
        return repository.existsByIDNumberAndIDTypeAndNationality(idNumber, idType, nationality);
    }

    private final IdentificationUSRepository repository;
    private final IdentificationUSPM persistenceMapper;
}