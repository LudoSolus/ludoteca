package com.projectLudoteca.ludoteca.infrastructure.security.config;

import com.projectLudoteca.ludoteca.command.requestPasswordReset.RequestPasswordResetHandler;
import com.projectLudoteca.ludoteca.common.exception.ExceptionDelegationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private ExceptionDelegationFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestPasswordResetHandler requestPasswordResetHandler) throws Exception {
        http
                // 🔒 Desativa CSRF para APIs REST
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth -> auth
                        // ✅ Rotas públicas (liberadas)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/auth/**",          // login e register
                                "/swagger-ui/**",    // documentação
                                "/v3/api-docs/**",
                                "/commands/users/register",
                                "/commands/users/login",
                                "/commands/users/request-password-reset",
                                "/commands/users/confirmation-password-reset",
                                "/queries/users/**",
                                "/commands/educational-institutions/register",
                                "/queries/educational-institutions/**"
                        ).permitAll()
                        .requestMatchers("/commands/admin/**", "/queries/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // Erros 403 e 401
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"mensagem\": \"Não autenticado. Token ausente ou inválido.\"}");
                        })

                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"mensagem\": \"Acesso negado. Privilégio insuficiente.\"}");
                        })
                )

                // 🔑 Desativa sessões — usa JWT (stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, JwtAuthFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",   // Angular local
                "https://ludoteca.vercel.app", // Staging
                "https://seu-dominio.com"  // Produção
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Permite enviar cookies/JWT

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
