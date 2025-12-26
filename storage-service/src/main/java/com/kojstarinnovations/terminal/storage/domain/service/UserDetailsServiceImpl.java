package com.kojstarinnovations.terminal.storage.domain.service;

import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImpl - Service class for user details
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Service
public class UserDetailsServiceImpl {

    /**
     * Get the current authenticated user
     *
     * @return PrincipalUser
     */
    public PrincipalUser principalUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof PrincipalUser ? (PrincipalUser) authentication.getPrincipal() : null;
    }
}