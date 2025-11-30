package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.us.domain.opextends.RolOP;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl.RolPM;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * RolPA - Persistence Adapter for Rol
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class RolPA implements RolOP {

    /**
     * existsById method
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean existsById(String id) {
        return rolRepository.existsById(id);
    }

    /**
     * Check if Rol exists by Rol Name
     *
     * @param rolName Rol Name
     * @return Boolean
     */
    @Override
    public boolean existsByRolName(RolName rolName) {
        return rolRepository.existsByRolName(rolName);
    }

    /**
     * This method saves a model
     *
     * @param dto the model to be saved
     * @return the saved model
     */
    @Override
    public RolDTO save(RolDTO dto) {
        return persistenceMapper.entityToDTO(
                rolRepository.save(
                        persistenceMapper.dtoToEntity(dto)
                )
        );
    }

    /**
     * This method gets a model by id
     *
     * @param id the id of the model to be fetched
     * @return the fetched model
     */
    @Override
    public Optional<RolDTO> getById(String id) {
        return rolRepository.findById(id)
                .map(persistenceMapper::entityToDTO);
    }

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    @Override
    public Page<RolDTO> getPage(Pageable pageable) {
        return rolRepository.findAll(pageable)
                .map(persistenceMapper::entityToDTO);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<RolDTO> getAll() {
        return rolRepository.findAll()
                .stream()
                .map(persistenceMapper::entityToDTO)
                .toList();
    }

    /**
     * This method updates a model
     *
     * @param dto     the model to be updated
     * @param integer the id of the model to be updated
     * @return the updated model
     */
    @Override
    public RolDTO updateById(RolDTO dto, String integer) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get Rol by Rol Name
     *
     * @param rolName Rol Name
     * @return Rol DTO
     */
    @Override
    public Optional<RolDTO> getByRolName(RolName rolName) {
        return rolRepository.findByRolName(rolName)
                .map(persistenceMapper::entityToDTO);
    }

    /**
     * This method deletes a model by id
     *
     * @param id the id of the model to be deleted
     */
    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private final RolRepository rolRepository;
    private final RolPM persistenceMapper;
}