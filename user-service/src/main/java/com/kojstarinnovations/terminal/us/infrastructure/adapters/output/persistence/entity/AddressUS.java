package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import com.kojstarinnovations.terminal.shared.entity.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AddressUS - Extends the Address entity and represents the persistence of the Address for the users in the database,
 * the id is generated pre-persist and the status is an enum.
 * The user_id is a foreign key to the user table.
 * The user is a lazy fetch.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "addresses")
@EqualsAndHashCode(callSuper = true)
public class AddressUS extends Address {

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
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.ADDRESS_ID.getCode(), 8);
        }
    }
}