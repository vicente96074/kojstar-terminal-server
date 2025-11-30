package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRol - This entity represents the persistence of the UserRol entity in the database,
 * the user_id and rol_id are composite keys.
 * The user_id is a foreign key to the user entity and the rol_id is a foreign key to the rol entity.
 * The user and rol are lazy fetch.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_rol")
@IdClass(UserRolId.class)
public class UserRol {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "rol_id")
    private String rolId;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Rol rol;
}