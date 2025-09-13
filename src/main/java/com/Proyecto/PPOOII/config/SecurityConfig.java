package com.Proyecto.PPOOII.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para APIs
            .authorizeHttpRequests(auth -> auth
                // Servicios públicos (Entrega 2)
                .requestMatchers("/api/public/**").permitAll()
                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Habilitar validación JWT

        return http.build();
    }
}

