package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.entity.BasicAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "fiscal_direction")
public class FiscalDirection extends BasicAudit {

    @Id
    private String id;

    @Column(unique = true)
    private String abbrev;

    @Column(name = "identifier_number", unique = true)
    private String identifierNumber;

    @Column(name = "fiscal_direction", unique = true)
    private String fiscalDirection;

    private String street;

    private String city;

    private String state;

    private String country;

    private String description;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUIDHelper.generateUUID(PrefixCodesISO.FISCAL_DIRECTION_ID.getCode(), 8);
        }
    }
}