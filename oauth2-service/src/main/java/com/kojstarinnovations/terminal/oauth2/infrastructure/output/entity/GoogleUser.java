package com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import com.kojstarinnovations.terminal.shared.entity.BasicAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "google_users")
public class GoogleUser extends BasicAudit {

    @Id
    @Column(name = "id", length = 15)
    private String id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;

    private String storeId;
    private String clientVersion;
    private String deviceInfo;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "google_user_rol",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "google_user_access",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id")
    )
    private List<Access> accesses;

    @OneToMany(mappedBy = "googleUser", fetch = FetchType.LAZY)
    private List<GoogleUserAccess> googleUserAccesses;

    @OneToMany(mappedBy = "googleUser", fetch = FetchType.LAZY)
    private List<GoogleUserRol> googleUserRoles;

    @PrePersist
    private void prePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.STORE_ID.getCode() + PrefixCodesISO.GOOGLE_USER, 10);
        }
    }
}