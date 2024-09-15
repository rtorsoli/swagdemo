package com.example.wallet.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.wallet.converter.WalletConverter;
import com.example.wallet.exception.AddressAlreadyExistsException;
import com.example.wallet.exception.EntityNotFoundException;
import com.example.wallet.exception.TenantNotFoundException;
import com.example.wallet.model.dto.WalletDTO;
import com.example.wallet.model.persistence.WalletPersistent;
import com.example.wallet.repository.TenantRepository;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WalletServiceImpl implements WalletService {

  @Autowired
  private WalletRepository walletRepository;
  
  @Autowired
  private TenantRepository tenantRepository;
  
  @Autowired
  private WalletConverter walletConverter;

  Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
 
  @Override
  public Flux<WalletDTO> findByTenantId(Long tenantId) {
    return walletRepository.findByTenantId(tenantId).map(walletConverter.persistentToDto());
  }

  @Override
  public Mono<WalletDTO> findByAddress(String address) {
    return walletRepository.findByAddress(address).map(walletConverter.persistentToDto());
  }

  @Override
  public Mono<WalletDTO> findById(Long id) {
    return walletRepository.findById(id).map(walletConverter.persistentToDto());
  }

  @Override
  public Mono<WalletDTO> insert(WalletDTO wallet) {
    WalletPersistent persistent = walletConverter.dtoToPersistent().apply(wallet);
    persistent.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
    
    return walletRepository.findByAddress(persistent.getAddress())
        .flatMap(w -> {
          throw new AddressAlreadyExistsException(HttpStatus.BAD_REQUEST,
              String.format("Address already exists", wallet.getAddress()));
        })
        .switchIfEmpty(Mono.just(tenantRepository.findById(persistent.getTenantId())))
        .switchIfEmpty(Mono.error(
            new TenantNotFoundException(HttpStatus.NOT_FOUND, String.format("Tenant %d not found", persistent.getTenantId()))))
        .flatMap(w -> walletRepository.save(persistent))
        .flatMap(w -> Mono.just(walletConverter.persistentToDto().apply(w)));

  }

  @Override
  public Mono<WalletDTO> update(WalletDTO wallet) {
    WalletPersistent updated = walletConverter.dtoToPersistent().apply(wallet);
    
    return walletRepository.findById(wallet.getId())
        .switchIfEmpty(Mono.error(
            new EntityNotFoundException(HttpStatus.NOT_FOUND, String.format("Wallet %d not found", wallet.getId()))))
        .flatMap(persistent -> walletRepository.save(updated.setCreatedAt(persistent.getCreatedAt())))
        .map(walletConverter.persistentToDto());
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return walletRepository.deleteById(id);
  }

}