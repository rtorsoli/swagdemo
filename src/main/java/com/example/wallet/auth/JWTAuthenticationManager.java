package com.example.wallet.auth;


import com.example.wallet.exception.AuthorizationException;
import com.example.wallet.repository.TenantRepository;
import com.example.wallet.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private TenantRepository tenantRepository;

    // private Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username = jwtUtil.extractUsername(token);
        return tenantRepository.findByNicknameOrEmail(username) 
                .map(userDetails -> {

                    if (jwtUtil.validateToken(token, userDetails.getNickname(), userDetails.getEmail())) {
                        String role = "USER";
                        return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                        );
                    } else {
                        throw new AuthorizationException(HttpStatus.FORBIDDEN,"Invalid JWT token");
                    }
                });
    }

    public ServerAuthenticationConverter authenticationConverter() {
        return new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    return Mono.just(SecurityContextHolder.getContext().getAuthentication());
                }
                return Mono.empty();
            }
        };
    }
}