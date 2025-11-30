package com.kojstarinnovations.terminal.commons.pm;

import org.mapstruct.Mapper;

/**
 * PersistenceMapper - Interface for persistence mappers
 *
 * @param <ENTITY> the entity object
 * @param <DTO>    the data transfer object
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Mapper
public interface PersistenceMapper<ENTITY, DTO> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    ENTITY dtoToEntity(DTO dto);

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    DTO entityToDTO(ENTITY entity);
}