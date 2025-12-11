package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.entity.BasicAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.kojstarinnovations.terminal.commons.data.constants.PersistenceConstants.AUTH_USERS_TABLE_NAME;

/**
 * User - This entity represents the persistence of the User entity in the database,
 * the id is generated pre-persist and the user has a many-to-many relationship with the Rol and Access entities.
 * The user has a one-to-many relationship with the Identification and Address entities.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = AUTH_USERS_TABLE_NAME)
@EqualsAndHashCode(callSuper = true)
public class User extends BasicAudit {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "first_surname")
    private String firstSurname;

    @Column(name = "second_surname")
    private String secondSurname;

    @Column(name = "marriage_surname")
    private String marriageSurname;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "store_branch_id")
    private String storeBranchId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "user_setting_id")
    private String userSettingId;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_rol",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_access",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id")
    )
    private Set<Access> accesses = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<IdentificationUS> identificationsUS;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AddressUS> addressUS;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<TwoFactorUser> twoFactorUsers;

    /**
     * PrePersist - This method is called before the entity is persisted in the database,
     * if the id is null it will generate a new UUID.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.USER_ID.getCode(), 15);
        }
    }
}