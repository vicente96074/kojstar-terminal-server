package com.kojstarinnovations.terminal.us.application.data.request;

import com.kojstarinnovations.terminal.commons.data.transport.commons.IdentificationRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * IdentificationUSRequest - Transport object for identification requests
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IdentificationUSRequest extends IdentificationRequest {
    private String userId;
}