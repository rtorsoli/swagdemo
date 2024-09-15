package com.example.wallet.service;

import com.example.wallet.model.dto.LoginDTO;
import reactor.core.publisher.Mono;

public interface LoginService {
  Mono<LoginDTO> login(LoginDTO login);
}