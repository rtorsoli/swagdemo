package com.example.wallet.service;

import com.example.wallet.util.TokenInfo;

import reactor.core.publisher.Mono;

public interface SecurityService {

    Mono<TokenInfo> authenticate(String username, String password);

}