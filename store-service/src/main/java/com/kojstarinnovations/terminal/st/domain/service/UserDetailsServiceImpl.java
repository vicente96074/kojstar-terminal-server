package com.kojstarinnovations.terminal.st.domain.service;

import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImpl - Service to get the principal user from the authentication context
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Service
public class UserDetailsServiceImpl {

    /**
     * Get the principal user from the authentication context
     *
     * @return PrincipalUser the principal user
     */
    public PrincipalUser getUserFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof PrincipalUser ? (PrincipalUser) authentication.getPrincipal() : null;
    }
}