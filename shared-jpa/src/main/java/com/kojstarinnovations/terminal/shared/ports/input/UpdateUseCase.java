package com.kojstarinnovations.terminal.shared.ports.input;

/**
 * UpdateUseCase
 *
 * @param <Request>  the object to be updated
 * @param <Response> the updated object
 * @param <ID>       id of the object to be updated
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface UpdateUseCase<Request, Response, ID> {

    /**
     * Update object by id
     *
     * @param request the object to be updated
     * @param id      id of the object to be updated
     * @return QueryResponse the updated object
     */
    Response updateById(Request request, ID id);
}
