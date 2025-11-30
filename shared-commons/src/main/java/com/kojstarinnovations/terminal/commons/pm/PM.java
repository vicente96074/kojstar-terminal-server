package com.kojstarinnovations.terminal.commons.pm;

public interface PM<ENTITY, DTO, RESPONSE> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    ENTITY dtoToEntity(DTO dto);

    /**
     * Maps an entity object to a payload
     *
     * @param entity the entity to be mapped
     * @return the mapped payload object
     */
    RESPONSE entityToResponse(ENTITY entity);
}