package com.kojstarinnovations.terminal.shared.ports.output;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OP<DTO, RESPONSE, ID> {

    /**
     * Method to save a modelDto
     *
     * @param dto the modelDto to be saved
     * @return modelDto
     */
    RESPONSE save(DTO dto);

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    Optional<RESPONSE> getById(ID id);

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    Page<RESPONSE> getPage(Pageable pageable);

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    List<RESPONSE> getAll();

    /**
     * Method to update a modelDto by id
     *
     * @param dto the modelDto to be updated
     * @param id  the id of the modelDto to be updated
     * @return modelDto updated
     */
    RESPONSE updateById(DTO dto, ID id);

    /**
     * Method to delete a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    void deleteById(ID id);

    /**
     * Method to check if a modelDto exists by id
     *
     * @param id the id of the modelDto to be checked
     * @return true if the modelDto exists, false otherwise
     */
    boolean existsById(ID id);
}