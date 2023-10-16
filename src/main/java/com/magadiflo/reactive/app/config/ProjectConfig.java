package com.magadiflo.reactive.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ProjectConfig {

    // Añade un ReactiveUserDetailsService al contexto Spring
    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("12345")
                .authorities("read")
                .build();

        // Crea un MapReactiveUserDetailsService para gestionar las instancias de UserDetails
        return new MapReactiveUserDetailsService(admin);
    }

    // Agrega un PasswordEncoder al contexto de Spring
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
