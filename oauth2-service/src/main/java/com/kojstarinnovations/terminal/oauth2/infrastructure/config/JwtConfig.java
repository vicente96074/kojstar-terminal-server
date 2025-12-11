package com.kojstarinnovations.terminal.oauth2.infrastructure.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Configuration
public class JwtConfig {

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        RSAKey rsaKey = loadRSAKey();
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(String.format("%s%s", hostIssuer, "/oauth2/jwks")).build();
    }

    private RSAKey loadRSAKey() throws Exception {
        KeyStore.PrivateKeyEntry entry = getKeyStoreEntry();
        RSAPublicKey publicKey = (RSAPublicKey) entry.getCertificate().getPublicKey();
        RSAPrivateKey privateKey = (RSAPrivateKey) entry.getPrivateKey();

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(keyAlias)
                .build();
    }

    private KeyStore.PrivateKeyEntry getKeyStoreEntry() throws Exception {
        ClassPathResource resource = new ClassPathResource(keystoreLocation);
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(resource.getInputStream(), keystorePassword.toCharArray());
        return (KeyStore.PrivateKeyEntry) keystore.getEntry(
                keyAlias,
                new KeyStore.PasswordProtection(keyPassword.toCharArray())
        );
    }

    @Value("${server.host-issuer}")
    private String hostIssuer;

    @Value("${jwt.key.keystore-location}")
    private String keystoreLocation;

    @Value("${jwt.key.keystore-password}")
    private String keystorePassword;

    @Value("${jwt.key.key-alias}")
    private String keyAlias;

    @Value("${jwt.key.key-password}")
    private String keyPassword;
}