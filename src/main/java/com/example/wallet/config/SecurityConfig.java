package com.example.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
          .withUsername("admin")
          .password(encoder().encode("password"))
          .roles("ADMIN")
          .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/api/v1/tenants/**").authenticated()
                .pathMatchers("/api/v1/wallets/**").authenticated()
                        .pathMatchers("/api/v1/login").permitAll()
                        .pathMatchers("/api/v1/registration").permitAll()
                        .pathMatchers("/api/health").permitAll()
                        .pathMatchers("/api/swagger-ui/**", "/api/v3/api-docs/**").permitAll())
          .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

   
