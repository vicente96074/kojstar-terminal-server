package com.kojstarinnovations.terminal.shared.ports.input;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Generic get use case
 *
 * @param <Response>
 * @param <ID>
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface GetUseCase<Response, ID> {

    /**
     * Get object by id
     *
     * @param id id of the object to be retrieved
     * @return QueryResponse
     */
    Response getById(ID id);

    /**
     * Get page of objects
     *
     * @param pageable the pageable object
     * @return QueryResponse
     */
    Page<Response> getPage(Pageable pageable);

    /**
     * Get all objects
     *
     * @return QueryResponse
     */
    List<Response> getAll();
}