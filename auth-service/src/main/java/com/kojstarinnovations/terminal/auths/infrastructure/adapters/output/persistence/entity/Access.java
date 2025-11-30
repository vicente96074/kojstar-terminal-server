package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.coverters.enums.AccessNameConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Access - This entity represents the persistence of the Access entity in the database,
 * the id is generated pre-persist and the accessName is an enum.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "access")
@Builder
public class Access {

    @Id
    private String id;

    @Column(name = "access_name", columnDefinition = "VARCHAR(25) DEFAULT 'CUSTOMER'")
    @Convert(converter = AccessNameConverter.class)
    private AccessName accessName;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.ACCESS_ID.getCode(), 8);
        }
    }
}