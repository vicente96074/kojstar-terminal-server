package com.kojstarinnovations.terminal.commons.dm;

import org.mapstruct.Mapper;

/**
 * DomainMapper - Interface for domain mappers
 *
 * @param <DTO>      the dto object
 * @param <REQUEST>  the transport object
 * @param <RESPONSE> the payload object
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Mapper
public interface DomainMapper<DTO, REQUEST, RESPONSE> {

    /**
     * Maps a transport object to a dto object
     *
     * @param request the transport to be mapped
     * @return the mapped dto object
     */
    DTO requestToDTO(REQUEST request);

    /**
     * Maps a dto object to a payload object
     *
     * @param dto the dto to be mapped
     * @return the mapped payload object
     */
    RESPONSE dtoToResponse(DTO dto);
}