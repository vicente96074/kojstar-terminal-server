package com.kojstarinnovations.terminal.commons.data.enums.iso;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SubdivisionRegionCodeISO - Enum for Subdivision Region Code ISO
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-2:GT">ISO 3166-2:GT</a>
 */
@Getter
public enum SubdivisionRegionCodeISO {

    /* GT */
    /**
     * Alta Verapaz
     **/
    GT_AV_CO("GT-AV-CO", "Cobán"),
    GT_AV_FR("GT-AV-FR", "Fray Bartolomé de las Casas"),
    GT_AV_LA("GT-AV-LA", "Lanquín"),
    GT_AV_PA("GT-AV-PA", "Panzós"),
    GT_AV_SA("GT-AV-SA", "San Cristóbal Verapaz"),
    GT_AV_SE("GT-AV-SM", "San Miguel Tucurú"),
    GT_AV_SE2("GT-AV-SV", "Santa Cruz Verapaz"),
    GT_AV_SE3("GT-AV-SC", "Santa María Cahabón"),
    GT_AV_SE4("GT-AV-SE", "Senahú"),
    GT_AV_TA("GT-AV-TA", "Tactic"),
    GT_AV_TA2("GT-AV-TH", "Tamahú"),
    GT_AV_TA3("GT-AV-TC", "Tucurú"),

    /**
     * Baja Verapaz
     **/
    GT_BV_CA("GT-BV-CA", "Cubulco"),
    GT_BV_EL("GT-BV-EL", "El Chol"),

    /**
     * Quetzaltenango
     **/
    GT_QZ_AL("GT-QZ-AL", "Almolonga"),
    GT_QZ_CA("GT-QZ-CA", "Cabricán"),
    GT_QZ_CO("GT-QZ-CO", "Cajolá"),
    GT_QZ_CU("GT-QZ-CU", "Cantel"),
    GT_QZ_CO2("GT-QZ-CT", "Coatepeque"),
    GT_QZ_CO3("GT-QZ-CM", "Colomba"),
    GT_QZ_CO4("GT-QZ-CC", "Concepción Chiquirichapa"),
    GT_QZ_FI("GT-QZ-FI", "Flores Costa Cuca"),
    GT_QZ_GA("GT-QZ-GA", "Génova"),
    GT_QZ_HO("GT-QZ-HO", "Huitán"),
    GT_QZ_LA("GT-QZ-LA", "La Esperanza"),
    GT_QZ_MA("GT-QZ-MA", "Malacatancito"),
    GT_QZ_OB("GT-QZ-OB", "Olintepeque"),
    GT_QZ_PA("GT-QZ-PA", "Palestina de Los Altos"),
    GT_QZ_QU("GT-QZ-QU", "Quetzaltenango"),
    GT_QZ_SA("GT-QZ-SA", "Salcajá"),
    GT_QZ_SA2("GT-QZ-SC", "San Carlos Sija"),
    GT_QZ_SA3("GT-QZ-SU", "San Francisco La Unión"),
    GT_QZ_SA4("GT-QZ-SQ", "San Martín Sacatepéquez"),
    GT_QZ_SA5("GT-QZ-SM", "San Mateo"),
    GT_QZ_SA6("GT-QZ-SS", "San Miguel Sigüilá"),
    GT_QZ_SA7("GT-QZ-SI", "Sibilia"),
    GT_QZ_ZU("GT-QZ-ZU", "Zunil");

    SubdivisionRegionCodeISO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Optional<SubdivisionRegionCodeISO> fromCode(String code) {
        if (code == null || code.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(CODE_MAP.get(code.toUpperCase()));
    }

    private final String code;
    private final String name;

    private static final Map<String, SubdivisionRegionCodeISO> CODE_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            subregion -> subregion.code.toUpperCase(),
                            subregion -> subregion
                    ));
}