package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.shared.entity.BasicAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "branch_offices")
public class BranchOffice extends BasicAudit {

    @Id
    private String id;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "location_id", unique = true)
    private String locationId;


    @Column(nullable = false)
    private String nit;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "closing_hours")
    private String closingHours;

    @Column(name = "logo_name")
    private String logoName;

    @Column(name = "general_manager") // General manager (user-service/user_id)
    private String generalManager;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private FiscalDirection fiscalDirection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Store store;

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUIDHelper.generateUUID(PrefixCodesISO.STORE_ID.getCode(), 8);
        }
    }
}