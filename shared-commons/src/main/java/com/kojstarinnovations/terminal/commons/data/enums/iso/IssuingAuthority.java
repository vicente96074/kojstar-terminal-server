package com.kojstarinnovations.terminal.commons.data.enums.iso;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum IssuingAuthority {
    RENAP("RENAP", "Registro Nacional de las Personas"),
    SAT("SAT", "Superintendencia de Administración Tributaria");

    IssuingAuthority(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Optional<IssuingAuthority> fromCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(CODE_MAP.get(code.toUpperCase()));
    }

    private final String code;
    private final String name;


    private static final Map<String, IssuingAuthority> CODE_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            authorityCodeISO -> authorityCodeISO.code.toUpperCase(),
                            authorityCodeISO -> authorityCodeISO
                    ));
}