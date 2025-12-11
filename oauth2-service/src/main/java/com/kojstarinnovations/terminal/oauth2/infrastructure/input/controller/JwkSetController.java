package com.kojstarinnovations.terminal.oauth2.infrastructure.input.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;  // ¡Este es el importante!
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class JwkSetController {
    private final JWKSet jwkSet;  // Cambia el tipo aquí

    public JwkSetController(JWKSource<SecurityContext> jwkSource) {
        // Obtén el JWKSet directamente si es posible
        this.jwkSet = ((ImmutableJWKSet<SecurityContext>) jwkSource).getJWKSet();
    }

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> getJwks() {
        //log.info("Getting JWK set");
        //log.info("Getting JWK set");
        return jwkSet.toJSONObject();
    }
}