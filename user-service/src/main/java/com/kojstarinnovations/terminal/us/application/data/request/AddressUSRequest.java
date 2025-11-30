package com.kojstarinnovations.terminal.us.application.data.request;

import com.kojstarinnovations.terminal.commons.data.transport.commons.AddressRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * AddressUSRequest - Transport object for US address requests
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressUSRequest extends AddressRequest {
    private String userId;
}