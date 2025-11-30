package com.kojstarinnovations.terminal.shared.ports.output;

public interface ExistsOutputPort<ID> {

    /**
     * Method to check if a modelDto exists by id
     *
     * @param id the id of the modelDto to be checked
     * @return true if the modelDto exists, false otherwise
     */
    boolean existsById(ID id);
}
