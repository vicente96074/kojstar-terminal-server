package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * UserPM - Persistence Mapper for User entity and dto
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class UserPM implements PersistenceMapper<User, UserDTO> {

    /**
     * Maps a dto object to an entity object
     *
     * @param dto the dto to be mapped
     * @return the mapped entity object
     */
    @Override
    public User dtoToEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    /**
     * Maps an entity object to a dto object
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    @Override
    public UserDTO entityToDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    /**
     * Maps an entity object to a dto object with access and roles
     *
     * @param entity the entity to be mapped
     * @return the mapped dto object
     */
    public UserDTO entityToDTOWithAccessAndRoles(User entity) {
        UserDTO dto = modelMapper.map(entity, UserDTO.class);

        if (entity.getAccesses() != null && !entity.getAccesses().isEmpty()) {
            dto.setAccessDTOs(
                    entity.getAccesses()
                            .stream()
                            .map(access -> modelMapper.map(access, AccessDTO.class))
                            .toList()
            );
        }

        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            dto.setRolDTOs(
                    entity.getRoles()
                            .stream()
                            .map(rol -> modelMapper.map(rol, RolDTO.class))
                            .toList()
            );
        }

        return dto;
    }

    public UserResponse entityToUserResponse(User entity) {
        return modelMapper.map(entity, UserResponse.class);
    }

    private final ModelMapperCustomized modelMapper;
}