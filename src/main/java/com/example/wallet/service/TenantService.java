package com.example.wallet.service;

import com.example.wallet.model.dto.TenantDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TenantService {

  Flux<TenantDTO> findAll(int offset, int limit);

  Flux<TenantDTO> findByLastName(String lastname, int offset, int limit);

  Mono<TenantDTO> findById(Long id);

  Mono<TenantDTO> insert(TenantDTO tenant);

  Mono<TenantDTO> update(TenantDTO tenant);

  Mono<Void> deleteById(Long id);

}