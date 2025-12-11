package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.entity;

import com.kojstarinnovations.terminal.commons.data.enums.BusinessType;
import com.kojstarinnovations.terminal.commons.data.enums.PrefixCodesISO;
import com.kojstarinnovations.terminal.shared.coverters.enums.BusinessTypeConverter;
import com.kojstarinnovations.terminal.shared.entity.BasicAudit;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stores")
public class Store extends BasicAudit {

    @Id
    private String id;

    @Column(name = "location_id")
    private Integer locationId;
    private String ceo;
    private String nit;
    private String name;
    private String description;
    private String phone;
    private String email;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "business_type")
    @Convert(converter = BusinessTypeConverter.class)
    private BusinessType businessType;

    @Column(name = "web_site")
    private String webSite;

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = PrefixCodesISO.STORE_ID.getCode();
        }
    }
}