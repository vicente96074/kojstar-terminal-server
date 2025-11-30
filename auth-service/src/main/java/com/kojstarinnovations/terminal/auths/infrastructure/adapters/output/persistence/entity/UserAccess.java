package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserAccess - This entity represents the persistence of the UserAccess entity in the database,
 * the id is a composite key of the userId and accessId.
 * The user_id is a foreign key to the user table.
 * The access_id is a foreign key to the access table.
 * The user and access are lazy fetch.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserAccessId.class)
@Entity(name = "user_access")
public class UserAccess {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "access_id")
    private String accessId;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Access access;
}