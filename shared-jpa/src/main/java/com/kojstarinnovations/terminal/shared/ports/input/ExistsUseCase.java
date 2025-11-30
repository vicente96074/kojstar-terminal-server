package com.kojstarinnovations.terminal.shared.ports.input;

/**
 * Generic get use case
 *
 * @param <ID>
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface ExistsUseCase<ID> {

    /**
     * Check if an object exists by its id
     *
     * @param id id of the object to be retrieved
     * @return boolean
     */
    boolean existsById(ID id);
}