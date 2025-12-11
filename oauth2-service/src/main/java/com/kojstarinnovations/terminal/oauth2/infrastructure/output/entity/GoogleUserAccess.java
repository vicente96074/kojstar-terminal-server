package com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserAccessId.class)
@Entity(name = "google_user_access")
public class GoogleUserAccess {

    @Id
    @Column(name = "user_id", length = 15)
    private String userId;

    @Id
    @Column(name = "access_id", length = 10)
    private String accessId;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private GoogleUser googleUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Access access;
}