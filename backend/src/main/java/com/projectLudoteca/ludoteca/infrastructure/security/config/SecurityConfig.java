package com.projectLudoteca.ludoteca.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔒 Desativa CSRF para APIs REST
                .csrf(csrf -> csrf.disable())

                // 🚪 Autorizações de rotas
                .authorizeHttpRequests(auth -> auth
                        // ✅ Rotas públicas (liberadas)
                        .requestMatchers(
                                "/auth/**",          // login e register
                                "/swagger-ui/**",    // documentação
                                "/v3/api-docs/**",
                                "/commands/users/register",
                                "/queries/students/**"
                        ).permitAll()

                        // 🔐 Todas as outras precisam de autenticação
                        .anyRequest().authenticated()
                )

                // 🔑 Desativa sessões — usa JWT (stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    // 💬 Encoder de senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
