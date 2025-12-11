package com.kojstarinnovations.terminal.st.domain.model;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import com.kojstarinnovations.terminal.commons.data.enums.BusinessType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO extends BasicAuditDTO {
    private String id;
    private Integer locationId;
    private String ceo;

    private String nit;
    private String name;
    private String description;
    private String phone;
    private String email;
    private String filename;
    private BusinessType businessType;

    private String webSite;
}