package com.api.water_sytem_management_java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF (opcional, dependendo do projeto)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Permite acesso livre a endpoints públicos
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permite acesso livre ao Swagger
                        .anyRequest().authenticated() // Exige autenticação para outros endpoints
                )
                .formLogin(form -> form.disable()) // Remove a tela de login padrão
                .httpBasic(httpBasic -> httpBasic.disable()); // Remove autenticação básica

        return http.build();
    }
}
