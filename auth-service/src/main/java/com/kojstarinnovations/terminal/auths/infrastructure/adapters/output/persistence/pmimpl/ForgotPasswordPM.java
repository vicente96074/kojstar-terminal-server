package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.pmimpl;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity.ForgotPassword;
import com.kojstarinnovations.terminal.commons.data.dto.authservice.ForgotPasswordDTO;
import com.kojstarinnovations.terminal.commons.pm.PersistenceMapper;
import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * ForgotPasswordPM - This class is responsible for mapping the ForgotPassword entity to the ForgotPasswordDTO data transfer object and vice versa.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Component
@RequiredArgsConstructor
public class ForgotPasswordPM implements PersistenceMapper<ForgotPassword, ForgotPasswordDTO> {

    /**
     * The method dtoToEntity
     *
     * @param dto is the data transfer object
     * @return the entity
     */
    @Override
    public ForgotPassword dtoToEntity(ForgotPasswordDTO dto) {
        return modelMapper.map(dto, ForgotPassword.class);
    }

    /**
     * The method entityToDto
     *
     * @param entity is the entity
     * @return the data transfer object
     */
    @Override
    public ForgotPasswordDTO entityToDTO(ForgotPassword entity) {
        return modelMapper.map(entity, ForgotPasswordDTO.class);
    }

    private final ModelMapperCustomized modelMapper;
}