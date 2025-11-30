package com.kojstarinnovations.terminal.shared.ports.input;

/**
 * Generic delete use case
 *
 * @param <ID>
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface DeleteUseCase<ID> {

    /**
     * Delete an entity by its id with transactional support
     *
     * @param id the id of the entity to be deleted
     */
    void deleteById(ID id);
}