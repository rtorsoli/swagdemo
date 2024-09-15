package com.example.wallet.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.example.wallet.model.persistence.WalletPersistent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletRepository
        extends ReactiveCrudRepository<WalletPersistent, Long>, ReactiveSortingRepository<WalletPersistent, Long> {
    
    Flux<WalletPersistent> findByTenantId(Long tenantId);
    Mono<WalletPersistent> findByAddress(String address);
 
}