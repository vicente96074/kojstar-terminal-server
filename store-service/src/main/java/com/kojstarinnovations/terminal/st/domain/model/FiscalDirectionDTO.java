package com.kojstarinnovations.terminal.st.domain.model;

import com.kojstarinnovations.terminal.commons.data.dto.commons.BasicAuditDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiscalDirectionDTO extends BasicAuditDTO {
    private String id;
    private String abbrev;
    private String identifierNumber;
    private String fiscalDirection;
    private String street;
    private String city;
    private String state;
    private String country;
    private String description;
}