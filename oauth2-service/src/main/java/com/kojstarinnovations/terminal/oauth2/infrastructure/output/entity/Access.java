package com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.entity.AccessEnt;
import jakarta.persistence.*;
import lombok.*;

import static com.kojstarinnovations.terminal.commons.data.constants.PersistenceConstants.AUTH_ACCESSES_TABLE_NAME;

/**
 * Access - This entity represents the persistence of the Access entity in the database,
 * the id is generated pre-persist and the accessName is an enum.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = AUTH_ACCESSES_TABLE_NAME)
@Builder
public class Access extends AccessEnt {

    @Id
    @Column(name = "id", length = 10)
    private String id;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.ACCESS_ID.getCode(), 10);
        }
    }
}