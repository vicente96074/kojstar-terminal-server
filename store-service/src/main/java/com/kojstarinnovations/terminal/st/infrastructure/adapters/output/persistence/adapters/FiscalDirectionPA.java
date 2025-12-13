package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.adapters;

import com.kojstarinnovations.terminal.commons.data.constants.I18nCommonConstants;
import com.kojstarinnovations.terminal.commons.data.payload.storeservice.FiscalDirectionResponse;
import com.kojstarinnovations.terminal.st.domain.model.FiscalDirectionDTO;
import com.kojstarinnovations.terminal.st.domain.opextends.FiscalDirectionOP;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.pmimpl.FiscalDirectionPM;
import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.repository.FiscalDirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FiscalDirectionPA implements FiscalDirectionOP {

    /**
     * Method to save a modelDto
     *
     * @param dto the modelDto to be saved
     * @return modelDto
     */
    @Override
    public FiscalDirectionResponse save(FiscalDirectionDTO dto) {
        return mapper.entityToResponse(
                repository.save(mapper.dtoToEntity(dto))
        );
    }

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    @Override
    public Optional<FiscalDirectionResponse> getById(String id) {
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
    public Page<FiscalDirectionResponse> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToResponse);
    }

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    @Override
    public List<FiscalDirectionResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    /**
     * Method to update a modelDto by id
     *
     * @param dto the modelDto to be updated
     * @param s   the id of the modelDto to be updated
     * @return modelDto updated
     */
    @Override
    public FiscalDirectionResponse updateById(FiscalDirectionDTO dto, String s) {
        throw new UnsupportedOperationException(I18nCommonConstants.EXCEPTION_NOT_IMPLEMENTED_YET);
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(String id) {
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

    private final FiscalDirectionRepository repository;
    private final FiscalDirectionPM mapper;
}
