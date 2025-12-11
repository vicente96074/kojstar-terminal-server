package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity.User;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.AccessDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.RolDTO;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.AccessResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.RolResponse;
import com.kojstarinnovations.terminal.commons.data.payload.userservice.UserResponse;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * AuthPM - This class is responsible for mapping the User entity to the UserDTO data transfer object and vice versa.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthPM implements PersistenceMapper<User, UserDTO, UserResponse> {

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
     * Maps an entity to response
     *
     * @param entity persisted
     * @return payload
     */
    @Override
    public UserResponse entityToResponse(User entity) {
        return modelMapper.map(entity, UserResponse.class);
    }

    /**
     * Maps a User entity to a UserDTO with Access and Roles
     *
     * @param entity the entity
     * @return UserDTO data transfer object
     */
    public UserResponse entityToResponseWithAccessAndRoles(User entity) {
        //log.info("User entity: {}", entity.getUserRoles());

        UserResponse response = modelMapper.map(entity, UserResponse.class);

        if (entity.getUserAccesses() != null && !entity.getUserAccesses().isEmpty()) {
            response.setAccessResponses(entity.getUserAccesses().stream()
                    .filter(userAccess -> userAccess.getStatus() == Status.ACTIVE && userAccess.getAccess() != null)
                    .map(userAccess -> modelMapper.map(userAccess.getAccess(), AccessResponse.class))
                    .toList()
            );
        }

        if (entity.getUserRoles() != null && !entity.getUserRoles().isEmpty()) {
            response.setRolResponses(entity.getUserRoles().stream()
                    .filter(userRole -> userRole.getStatus() == Status.ACTIVE && userRole.getRol() != null)
                    .map(userRole -> modelMapper.map(userRole.getRol(), RolResponse.class))
                    .collect(Collectors.toList())
            );
        }

        return response;
    }

    /**
     * Maps a User entity to a UserDTO with Access and Roles
     *
     * @param entity the entity
     * @return UserDTO data transfer object
     */
    public UserDTO entityToDtoWithAccessAndRoles(User entity) {
        //log.info("User entity: {}", entity.getUserRoles());

        UserDTO response = modelMapper.map(entity, UserDTO.class);

        if (entity.getUserAccesses() != null && !entity.getUserAccesses().isEmpty()) {
            response.setAccessDTOs(entity.getUserAccesses().stream()
                    .filter(userAccess -> userAccess.getStatus() == Status.ACTIVE && userAccess.getAccess() != null)
                    .map(userAccess -> modelMapper.map(userAccess.getAccess(), AccessDTO.class))
                    .toList()
            );
        }

        if (entity.getUserRoles() != null && !entity.getUserRoles().isEmpty()) {
            response.setRolDTOs(entity.getUserRoles().stream()
                    .filter(userRole -> userRole.getStatus() == Status.ACTIVE && userRole.getRol() != null)
                    .map(userRole -> modelMapper.map(userRole.getRol(), RolDTO.class))
                    .collect(Collectors.toList())
            );
        }

        return response;
    }

    private final ModelMapperCustomized modelMapper;
}