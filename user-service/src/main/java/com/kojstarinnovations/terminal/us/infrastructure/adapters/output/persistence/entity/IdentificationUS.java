package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import com.kojstarinnovations.terminal.shared.entity.Identification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * IdentificationUS - Extends the Identification entity and represents the persistence of the Identification for the users in the database,
 * the id is generated pre-persist and the status is an enum.
 * The user_id is a foreign key to the user table.
 * The user is a lazy fetch.
 * Unique constraint composed by identification_number, identification_type and nationality.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "identifications")
@EqualsAndHashCode(callSuper = true)
@Table(name = "identifications", uniqueConstraints = @UniqueConstraint(columnNames = {"identification_number", "identification_type", "nationality"}))
public class IdentificationUS extends Identification {

    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * PrePersist - This method is called before the entity is persisted in the database,
     * if the id is null it will generate a new UUID.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.IDENTIFICATION_ID.getCode(), 8);
        }
    }
}