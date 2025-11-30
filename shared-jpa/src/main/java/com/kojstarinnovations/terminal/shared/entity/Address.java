package com.kojstarinnovations.terminal.shared.entity;

import com.kojstarinnovations.terminal.commons.data.enums.iso.CountryCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.RegionCodeISO;
import com.kojstarinnovations.terminal.commons.data.enums.iso.SubdivisionRegionCodeISO;
import com.kojstarinnovations.terminal.shared.coverters.enums.CountryCodeISOConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.RegionCodeISOConverter;
import com.kojstarinnovations.terminal.shared.coverters.enums.SubdivisionRegionCodeISOConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Address - Structure of the Address entity, this is a mapped superclass and is extended by other entities.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "subdivision_region_code", columnDefinition = "VARCHAR(8)")
    @Convert(converter = SubdivisionRegionCodeISOConverter.class)
    private SubdivisionRegionCodeISO subdivisionRegionCodeISO = SubdivisionRegionCodeISO.GT_QZ_QU;

    @Column(name = "region_code", columnDefinition = "VARCHAR(5)")
    @Convert(converter = RegionCodeISOConverter.class)
    private RegionCodeISO regionCodeISO = RegionCodeISO.GT_QZ;

    @Column(name = "country_code", columnDefinition = "VARCHAR(2)")
    @Convert(converter = CountryCodeISOConverter.class)
    private CountryCodeISO countryCodeISO = CountryCodeISO.GT;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;
}