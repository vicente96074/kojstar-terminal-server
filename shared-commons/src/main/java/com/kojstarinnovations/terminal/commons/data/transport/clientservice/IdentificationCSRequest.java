package com.kojstarinnovations.terminal.commons.data.transport.clientservice;

import com.kojstarinnovations.terminal.commons.data.transport.commons.IdentificationRequest;
import lombok.*;

/**
 * IdentificationCSRequest - Transport object for Identification with required fields
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IdentificationCSRequest extends IdentificationRequest {
    private String id;
    private String customerId;
}