package com.kojstarinnovations.terminal.auths.domain.service;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity.User;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.pmimpl.AuthPM;
import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.repository.AuthRepository;
import com.kojstarinnovations.terminal.commons.data.constants.ExceptionConstants;
import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UserDetailsServiceImpl - Implementation of the UserDetailsService interface for authentication purposes.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Load user by username or email.
     *
     * @param usernameOrEmail the username or email
     * @return UserDetails
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> opUser = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (opUser.isEmpty()) {
            throw new UsernameNotFoundException(ExceptionConstants.USER_NOT_FOUND);
        }

        UserDTO userDto = persistenceMapper.entityToDtoWithAccessAndRoles(opUser.get());
        //log.info("PrincipalUser: {}", user);
        return PrincipalUser.build(userDto);
    }

    public UserDetails loadUserBySub(String sub) throws UsernameNotFoundException {
        Optional<User> opUser = repository.findById(sub);

        if (opUser.isEmpty()) {
            throw new UsernameNotFoundException(ExceptionConstants.USER_NOT_FOUND);
        }

        UserDTO dto = persistenceMapper.entityToDtoWithAccessAndRoles(opUser.get());
        dto.setHasAnyTwoFactorActive(repository.hasAnyTwoFactorActive(sub));
        return PrincipalUser.build(dto);
    }

    private final AuthRepository repository;
    private final AuthPM persistenceMapper;
}