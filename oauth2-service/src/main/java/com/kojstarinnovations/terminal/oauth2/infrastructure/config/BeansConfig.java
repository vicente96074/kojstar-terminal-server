package com.kojstarinnovations.terminal.oauth2.infrastructure.config;

import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapperCustomized modelMapperCustomized() {
        return new ModelMapperCustomized();
    }
}