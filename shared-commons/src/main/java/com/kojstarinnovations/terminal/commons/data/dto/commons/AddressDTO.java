package com.kojstarinnovations.terminal.commons.data.dto.commons;

import com.kojstarinnovations.terminal.commons.data.enums.Status;
import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.SubdivisionRegionCodeISO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AddressDTO - Data Transfer Object for Address
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String transactionId;
    private String address;
    private String city;
    private SubdivisionRegionCodeISO subdivisionRegionCodeISO;
    private RegionCodeISO regionCodeISO;
    private CountryCodeISO countryCodeISO;
    private String postalCode;
    private Integer latitude;
    private Integer longitude;
    private Status status;
}