package com.kojstarinnovations.terminal.commons.data.enums.iso;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RegionCodeISO - Enum for Region Code ISO
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-2:GT">ISO 3166-2:GT</a>
 */
@Getter
public enum RegionCodeISO {
    NONE("NONE", "None"),
    /**
     * Guatemala regions
     */
    GT_AV("GT-AV", "Alta Verapaz"),
    GT_BV("GT-BV", "Baja Verapaz"),
    GT_CM("GT-CM", "Chimaltenango"),
    GT_CQ("GT-CQ", "Chiquimula"),
    GT_PR("GT-PR", "El Progreso"),
    GT_ES("GT-ES", "Escuintla"),
    GT_GU("GT-GU", "Guatemala"),
    GT_HU("GT-HU", "Huehuetenango"),
    GT_IZ("GT-IZ", "Izabal"),
    GT_JA("GT-JA", "Jalapa"),
    GT_JU("GT-JU", "Jutiapa"),
    GT_PE("GT-PE", "Petén"),
    GT_QZ("GT-QZ", "Quetzaltenango"),
    GT_QC("GT-QC", "Quiché"),
    GT_RE("GT-RE", "Retalhuleu"),
    GT_SA("GT-SA", "Sacatepequez"),
    GT_SM("GT-SM", "San Marcos"),
    GT_SR("GT-SR", "Santa Rosa"),
    GT_SO("GT-SO", "Sololá"),
    GT_SU("GT-SU", "Suchitepequez"),
    GT_TO("GT-TO", "Totonicapán"),
    GT_ZA("GT-ZA", "Zacapa"),

    /**
     * Mexico regions
     */
    MX_AG("MX-AG", "Aguascalientes"),

    /**
     * United States of America regions
     */
    US_AK("US-AK", "Alaska"),
    ;


    RegionCodeISO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Optional<RegionCodeISO> fromCode(String code) {
        if (code == null || code.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(CODE_MAP.get(code.toUpperCase()));
    }

    private final String code;
    private final String name;

    private static final Map<String, RegionCodeISO> CODE_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            region -> region.code.toUpperCase(),
                            region -> region
                    ));
}