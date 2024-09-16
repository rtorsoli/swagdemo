package com.example.wallet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wallet.exception.AuthorizationException;
import com.example.wallet.repository.TenantRepository;
import com.example.wallet.service.SecurityService;
import com.example.wallet.util.JWTUtil;
import com.example.wallet.util.TokenInfo;

import reactor.core.publisher.Mono;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private TenantRepository tenantRepository;
    
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JWTUtil jwtUtil;
  
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
  
    public Mono<TokenInfo> authenticate(String username, String password) {
        return tenantRepository.findByNicknameOrEmail(username)
                .flatMap(tenant -> {
                    // if (!tenant.isEnabled())
                    //     return Mono.error(new AuthException("Account disabled"));

                    if (!encoder.matches(password, tenant.getPassword()))
                        return Mono.error(new AuthorizationException(HttpStatus.FORBIDDEN, "Invalid password"));

                    return Mono.just(jwtUtil.generateToken(tenant).toBuilder()
                            .tenantId(tenant.getId())
                            .build());
                })
                .switchIfEmpty(Mono.error(new AuthorizationException(HttpStatus.FORBIDDEN, "User, " + username + " is not valid")));
    }
}