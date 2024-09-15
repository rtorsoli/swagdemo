package com.example.wallet.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.example.wallet.model.persistence.TenantPersistent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TenantRepository
        extends ReactiveCrudRepository<TenantPersistent, Long>, ReactiveSortingRepository<TenantPersistent, Long> {
    Flux<TenantPersistent> findAllBy(Pageable pageable);   

    Flux<TenantPersistent> findByLastname(String lastname, Pageable pageable);

    Mono<TenantPersistent> findByNickname(String nickname);

    Mono<TenantPersistent> findByEmail(String email);

    @Query("select * from tenants where nickname = $1 or email = $1")
    Mono<TenantPersistent> findByNicknameOrEmail(String username);
    
}