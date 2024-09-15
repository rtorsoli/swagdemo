package com.example.wallet.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface WalletHandler {

    Mono<ServerResponse> findByTenantId(final ServerRequest request);

    Mono<ServerResponse> findByAddress(final ServerRequest request);

    Mono<ServerResponse> findById(final ServerRequest request);
    
    Mono<ServerResponse> findByIdWithData(final ServerRequest request);
    
    Mono<ServerResponse> insert(final ServerRequest request);
    
    Mono<ServerResponse> update(final ServerRequest request);

    Mono<ServerResponse> delete(final ServerRequest request);
} 