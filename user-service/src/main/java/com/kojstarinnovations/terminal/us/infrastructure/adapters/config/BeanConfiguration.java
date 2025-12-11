package com.kojstarinnovations.terminal.us.infrastructure.adapters.config;

import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * BeanConfiguration - Configuration for beans like EventPublisher, ModelMapperCustomized, PasswordEncoder, AuthenticationManager
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Configuration
public class BeanConfiguration {

    /**
     * ModelMapperCustomized
     *
     * @return ModelMapperCustomized
     */
    @Bean
    public ModelMapperCustomized modelMapperCustomized() {
        return new ModelMapperCustomized();
    }

    /**
     * PasswordEncoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager
     *
     * @param authenticationConfiguration authenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }
}