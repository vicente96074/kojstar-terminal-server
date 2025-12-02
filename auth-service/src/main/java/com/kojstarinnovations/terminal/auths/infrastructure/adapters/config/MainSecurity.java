package com.kojstarinnovations.terminal.auths.infrastructure.adapters.config;

import com.kojstarinnovations.terminal.auths.domain.service.UserDetailsServiceImpl;
import com.kojstarinnovations.terminal.auths.jwt.JwtEntryPoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * This class configures the security filter chain.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Slf4j
@Getter
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class MainSecurity {

    /**
     * This method configures the security filter chain.
     *
     * @param http the HttpSecurity object
     * @return SecurityFilterChain the security filter chain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
        authenticationManager = builder.build();

        http.authenticationManager(authenticationManager)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exc -> exc.authenticationEntryPoint(jwtEntryPoint));

        return http.build();
    }

    private static final String[] AUTH_WHITELIST = {
            "/auth-service/auth/**",
            "/configuration/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v2/api-docs/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/favicon.ico",
            "/api/vault/test"
    };

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtEntryPoint jwtEntryPoint;

    private AuthenticationManager authenticationManager;
}