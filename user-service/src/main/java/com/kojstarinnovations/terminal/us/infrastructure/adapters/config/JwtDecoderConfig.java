package com.kojstarinnovations.terminal.us.infrastructure.adapters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtDecoderConfig {

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build();

        // Configuramos los validadores para asegurar que el 'iss' sea el correcto
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(hostIssuer);
        jwtDecoder.setJwtValidator(withIssuer);

        return jwtDecoder;
    }

    @Bean
    public RSAPublicKey rsaPublicKey() throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(keystoreLocation);

        byte[] keyBytes = resource.getInputStream().readAllBytes();
        String publicKeyPEM = new String(keyBytes)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("-----BEGIN CERTIFICATE-----", "") // Por si acaso exportaste el certificado
                .replace("-----END CERTIFICATE-----", "")
                .replaceAll("\\s", ""); // Elimina espacios, tabs y saltos de línea

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }

    @Value("${jwt.public-key-location}")
    private String keystoreLocation;

    @Value("${server.host-issuer}")
    private String hostIssuer;
}