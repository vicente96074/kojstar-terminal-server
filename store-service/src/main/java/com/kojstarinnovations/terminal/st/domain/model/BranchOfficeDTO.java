package com.kojstarinnovations.terminal.st.domain.model;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficeDTO extends BasicAuditDTO {
    private String id;
    private String storeId;
    private Integer locationId;
    private String nit;
    private String name;
    private String phone;
    private String email;
    private String openingHours;
    private String closingHours;
    private String logo;
    private String generalManager;
    private FiscalDirectionDTO fiscalDirectionDTO;
    private StoreDTO storeDTO;
}