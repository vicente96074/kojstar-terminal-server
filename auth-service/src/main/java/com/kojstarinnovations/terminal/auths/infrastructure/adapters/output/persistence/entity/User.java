package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.auths.vault.EncryptionConverter;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.kojstarinnovations.terminal.commons.data.constants.PersistenceConstants.AUTH_USERS_TABLE_NAME;

/**
 * User Entity Class Definition - JPA to represent the user table in the database
 * The id is generated pre-persist and the username and email are unique.
 * The roles and accesses are many-to-many relationships with the Rol and Access entities.
 * The storeBranchId and storeId are foreign keys to the StoreBranch and Store entities.
 * The settingId is a key from settings file.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = AUTH_USERS_TABLE_NAME)
public class User {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "first_surname")
    private String firstSurname;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAccess> userAccesses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRol> userRoles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TwoFactorUser> twoFactorUsers;
}