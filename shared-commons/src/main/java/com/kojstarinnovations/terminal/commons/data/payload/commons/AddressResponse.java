package com.kojstarinnovations.terminal.commons.data.payload.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AddressResponse - Payload for Address
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private String address;
    private String city;
    private String subdivisionRegionCode;
    private String regionCode;
    private String countryCode;
    private String postalCode;
    private Integer latitude;
    private Integer longitude;
}