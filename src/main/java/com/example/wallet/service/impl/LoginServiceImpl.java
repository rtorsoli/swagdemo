package com.example.wallet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.wallet.exception.InvalidLoginException;
import com.example.wallet.model.dto.LoginDTO;
import com.example.wallet.repository.TenantRepository;
import com.example.wallet.service.LoginService;

import reactor.core.publisher.Mono;

@Service
public class LoginServiceImpl implements LoginService {

  @Autowired
  private TenantRepository tenantRepository;
  
  @Autowired
  private PasswordEncoder encoder;
  // @Autowired
  // private LoginConverter loginConverter;
  
  Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
 
 
  public Mono<LoginDTO> login(LoginDTO login) {
    return tenantRepository.findByNicknameOrEmail(login.getUsername())
        .switchIfEmpty(Mono.error(
            new InvalidLoginException(HttpStatus.FORBIDDEN,
                String.format("Username %s not found", login.getUsername()))))
        .doOnNext(tenant -> {
          if (!encoder.matches(login.getPassword(), tenant.getPassword())) {
            throw new InvalidLoginException(HttpStatus.FORBIDDEN,"Password does not match");
          } 
        })   
        .map(tenant -> LoginDTO.builder().build());
  }
}