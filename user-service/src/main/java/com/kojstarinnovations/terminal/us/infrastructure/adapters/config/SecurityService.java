package com.kojstarinnovations.terminal.us.infrastructure.adapters.config;

import com.kojstarinnovations.terminal.shared.security.dto.PrincipalUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service("securityService")
public class SecurityService {

    public boolean hasAccess(String access) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> accesses = principalUser.getAccesses();

        //Validate if the user has the access
        for (GrantedAuthority grantedAuthority : accesses) {
            if (grantedAuthority.getAuthority().equals(access)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRole(String role) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> roles = principalUser.getAuthorities();

        //Validate if the user has the role
        for (GrantedAuthority grantedAuthority : roles) {
            if (grantedAuthority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyRole(String... roles) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principalUser.getAuthorities();

        //Validate if the user has any of the roles
        for (GrantedAuthority grantedAuthority : authorities) {
            for (String role : roles) {
                if (grantedAuthority.getAuthority().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAnyAccess(String... accesses) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principalUser.getAccesses();

        //Validate if the user has any of the accesses
        for (GrantedAuthority grantedAuthority : authorities) {
            for (String access : accesses) {
                if (grantedAuthority.getAuthority().equals(access)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Authentication authentication;
}
