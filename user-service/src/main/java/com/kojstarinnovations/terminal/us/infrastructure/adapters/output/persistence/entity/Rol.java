package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.enums.RolName;
import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.coverters.enums.RolNameConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.StatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rol Entity Class Definition - JPA to represent the rol table in the database
 * The id is generated pre-persist and the rolName is an enum.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "roles")
public class Rol {

    @Id
    private String id;

    @Column(name = "rol_name", columnDefinition = "VARCHAR(25) DEFAULT 'CUSTOMER'")
    @Convert(converter = RolNameConverter.class)
    private RolName rolName;

    @Column(name = "status", columnDefinition = "VARCHAR(12) DEFAULT 'PENDING'")
    @Convert(converter = StatusConverter.class)
    private Status status;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.ROLE_ID.getCode(), 8);
        }
    }
}