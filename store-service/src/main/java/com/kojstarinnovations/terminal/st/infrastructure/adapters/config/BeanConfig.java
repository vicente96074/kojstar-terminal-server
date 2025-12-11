package com.kojstarinnovations.terminal.st.infrastructure.adapters.config;

import com.kojstarinnovations.terminal.shared.mapper.ModelMapperCustomized;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapperCustomized modelMapperCustomized() {
        return new ModelMapperCustomized();
    }
}