package com.example.wallet.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface TenantHandler {

    Mono<ServerResponse> findAll(final ServerRequest request);

    Mono<ServerResponse> findByLastName(final ServerRequest request);

    Mono<ServerResponse> findById(final ServerRequest request);
    
    Mono<ServerResponse> registration(final ServerRequest request);
    
    Mono<ServerResponse> update(final ServerRequest request);

    Mono<ServerResponse> delete(final ServerRequest request);
} 