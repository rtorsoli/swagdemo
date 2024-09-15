package com.example.wallet.service;

import com.example.wallet.model.dto.WalletDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletService {

  Mono<WalletDTO> findById(Long id);

  Flux<WalletDTO> findByTenantId(Long tenantId);

  Mono<WalletDTO> findByAddress(String address);

  Mono<WalletDTO> insert(WalletDTO wallet);

  Mono<WalletDTO> update(WalletDTO wallet);

  Mono<Void> deleteById(Long id);

}