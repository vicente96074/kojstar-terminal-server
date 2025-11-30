package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity.User;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * AuthPM - This class is responsible for mapping the User entity to the UserDTO data transfer object and vice versa.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class AuthPM implements PersistenceMapper<User, UserDTO> {

    /**
     * Maps a UserDTO to a User entity
     *
     * @param dto the data transfer object
     * @return User entity
     */
    @Override
    public User dtoToEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    /**
     * Maps a User entity to a UserDTO
     *
     * @param entity the entity
     * @return UserDTO data transfer object
     */
    @Override
    public UserDTO entityToDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    /**
     * Maps a User entity to a UserDTO with Access and Roles
     *
     * @param entity the entity
     * @return UserDTO data transfer object
     */
    public UserDTO entityToDtoWithAccessAndRoles(User entity) {
        UserDTO dto = modelMapper.map(entity, UserDTO.class);

        if (entity.getUserAccesses() != null && !entity.getUserAccesses().isEmpty()) {
            dto.setAccessDTOs(entity.getUserAccesses().stream()
                    .filter(userAccess -> userAccess.getStatus() == Status.ACTIVE && userAccess.getAccess() != null)
                    .map(userAccess -> modelMapper.map(userAccess.getAccess(), AccessDTO.class))
                    .toList()
            );
        }

        if (entity.getUserRoles() != null && !entity.getUserRoles().isEmpty()) {
            dto.setRolDTOs(entity.getUserRoles().stream()
                    .filter(userRole -> userRole.getStatus() == Status.ACTIVE && userRole.getRol() != null)
                    .map(userRole -> modelMapper.map(userRole.getRol(), RolDTO.class))
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    private final ModelMapperCustomized modelMapper;
}