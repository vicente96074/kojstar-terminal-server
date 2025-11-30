package com.kojstarinnovations.terminal.commons.data.payload.clientservice;

import com.kojstarinnovations.terminal.commons.data.payload.commons.AuditAttributeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * IdentificationCSResponse - Payload for Identification with Audit Attributes
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdentificationCSResponse extends AuditAttributeResponse {
    private Integer id;
    private String dpi;
    private String passport;
    private String nit;
    private String socialSecurity;
    private String driverLicense;
    private Integer personId;
}
