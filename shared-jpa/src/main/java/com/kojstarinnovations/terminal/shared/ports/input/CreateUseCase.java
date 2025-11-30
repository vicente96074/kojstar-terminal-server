package com.kojstarinnovations.terminal.shared.ports.input;

/**
 * Generic save use case
 *
 * @param <Request>  Create request type
 * @param <Response> Query response type
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface CreateUseCase<Request, Response> {


    /**
     * Use case to save a request with transactional support
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    Response save(Request request);
}
