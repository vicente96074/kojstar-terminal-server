package com.kojstarinnovations.terminal.oauth2.infrastructure.input.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OidcConfigurationController {

    @Value("${server.host-issuer}")
    private String issuerUri;

    @GetMapping("/.well-known/openid-configuration")
    public Map<String, Object> getConfiguration() {
        return Map.of(
                "issuer", issuerUri,
                "authorization_endpoint", issuerUri + "/oauth2/authorize",
                "token_endpoint", issuerUri + "/oauth2/token",
                "jwks_uri", issuerUri + "/oauth2/jwks",
                "scopes_supported", List.of("openid", "profile", "email"),
                "response_types_supported", List.of("code"),
                "grant_types_supported", List.of("authorization_code", "refresh_token"),
                "subject_types_supported", List.of("public"),
                "id_token_signing_alg_values_supported", List.of("RS256"),
                "token_endpoint_auth_methods_supported", List.of("client_secret_basic")
        );
    }
}