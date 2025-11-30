package com.kojstarinnovations.terminal.shared.ports.output;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GetOutputPort<PAYLOAD, ID> {

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    Optional<PAYLOAD> getById(ID id);

    /**
     * Method to get a page of modelDto
     *
     * @param pageable the pageable object
     * @return Page<DTO>
     */
    Page<PAYLOAD> getPage(Pageable pageable);

    /**
     * Method to get all modelDto
     *
     * @return List<DTO>
     */
    List<PAYLOAD> getAll();
}