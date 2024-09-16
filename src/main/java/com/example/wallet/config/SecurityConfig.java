package com.example.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import com.example.wallet.auth.JWTAuthenticationManager;
import com.example.wallet.auth.SecurityContextRepository;


@EnableWebFluxSecurity
@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final JWTAuthenticationManager jwtAuthenticationManager;
   
    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(JWTAuthenticationManager jwtAuthenticationManager, SecurityContextRepository securityContextRepository) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.securityContextRepository = securityContextRepository;
    }
    
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authenticationManager(jwtAuthenticationManager)
                .securityContextRepository(securityContextRepository)
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/login").permitAll()
                        .pathMatchers("/api/v1/signup").permitAll()
                        .pathMatchers("/api/health").permitAll()
                        .pathMatchers("/api/swagger-ui/**", "/api/v3/api-docs/**").permitAll()
                        .anyExchange().authenticated())
                .build();                        
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

   
