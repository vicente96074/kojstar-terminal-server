package com.kojstarinnovations.terminal.auths.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfig {

    @Value("${jwt.key.keystore-location}")
    private String keystoreLocation;

    @Value("${jwt.key.keystore-password}")
    private String keystorePassword;

    @Value("${jwt.key.key-alias}")
    private String keyAlias;

    @Value("${jwt.key.key-password}")
    private String keyPassword;

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        RSAKey rsaKey = loadRSAKey();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        RSAKey rsaKey = loadRSAKey();
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @SneakyThrows
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        // En lugar de withJwkSetUri, usa la fuente local que ya cargó el Keystore
        return NimbusJwtDecoder.withPublicKey(loadRSAPublicKey()).build();
    }

    private RSAKey loadRSAKey() throws Exception {
        // Usamos DefaultResourceLoader para que soporte prefijos como "classpath:" o "file:"
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(keystoreLocation);

        KeyStore keystore = KeyStore.getInstance("JKS");

        // Es importante usar try-with-resources para cerrar el InputStream correctamente
        try (InputStream is = resource.getInputStream()) {
            keystore.load(is, keystorePassword.toCharArray());
        }

        KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) keystore.getEntry(
                keyAlias,
                new KeyStore.PasswordProtection(keyPassword.toCharArray())
        );

        if (entry == null) {
            throw new IllegalArgumentException("No se encontró la llave con el alias: " + keyAlias);
        }

        RSAPublicKey publicKey = (RSAPublicKey) entry.getCertificate().getPublicKey();
        RSAPrivateKey privateKey = (RSAPrivateKey) entry.getPrivateKey();

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(keyAlias)
                .build();
    }

    private RSAPublicKey loadRSAPublicKey() throws Exception {
        RSAKey rsaKey = loadRSAKey();
        return rsaKey.toRSAPublicKey();
    }
}