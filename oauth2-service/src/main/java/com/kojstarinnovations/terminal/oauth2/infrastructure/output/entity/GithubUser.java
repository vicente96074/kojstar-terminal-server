package com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "github_users")
public class GithubUser {

    @Id
    @Column(name = "id", length = 15)
    private String id;
    private String email;
    private String name;
    private String login;

    private String storeId;
    private String clientVersion;
    private String deviceInfo;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "github_user_rol",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "github_user_access",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id")
    )
    private List<Access> accesses;

    @OneToMany(mappedBy = "githubUser", fetch = FetchType.LAZY)
    private List<GithubUserAccess> githubUserAccesses;

    @OneToMany(mappedBy = "githubUser", fetch = FetchType.LAZY)
    private List<GithubUserRol> githubUserRoles;

    @PrePersist
    private void prePersist() {
        this.id = UUIDHelper.generateUUID("GITHUB", 15);
    }
}