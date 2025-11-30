package com.kojstarinnovations.terminal.shared.security.dto;

import com.kojstarinnovations.terminal.commons.data.dto.userservice.UserDTO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to create a PrincipalUser object that will be used to authenticate the user
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrincipalUser implements UserDetails {

    /**
     * This method is used to build a PrincipalUser object from a UserDTO object
     *
     * @param dto The user
     * @return A PrincipalUser object
     */
    public static PrincipalUser build(UserDTO dto) {

        List<GrantedAuthority> authorities =
                dto.getRolDTOs().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolName().name())).collect(Collectors.toList());

        List<GrantedAuthority> accesses =
                dto.getAccessDTOs().stream().map(access -> new SimpleGrantedAuthority(access.getAccessName().name())).collect(Collectors.toList());

        return PrincipalUser.builder()
                .sub(dto.getId())
                .storeBranchId(dto.getStoreBranchId())
                .storeId(dto.getStoreId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .authorities(authorities)
                .accesses(accesses)
                .status(dto.getStatus())
                .hasAnyTwoFactorActive(dto.getHasAnyTwoFactorActive())
                .build();
    }

    /**
     * This method is used to get the authorities of the user
     *
     * @return The authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * This method is used to get the accesses of the user
     *
     * @return The accesses
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * This method is used to get the username of the user
     *
     * @return The username
     */
    @Override
    public String getUsername() {
        return username;
    }

    private String sub;
    private String storeBranchId;
    private String storeId;
    private String username;
    private String password;
    private String provider;
    private Status status;
    private Boolean hasAnyTwoFactorActive;
    private Collection<? extends GrantedAuthority> authorities;
    private Collection<? extends GrantedAuthority> accesses;
}