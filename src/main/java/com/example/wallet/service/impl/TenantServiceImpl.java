package com.example.wallet.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.wallet.converter.TenantConverter;
import com.example.wallet.exception.EmailAlreadyExistsException;
import com.example.wallet.exception.EntityNotFoundException;
import com.example.wallet.exception.NickNameAlreadyExistsException;
import com.example.wallet.model.dto.TenantDTO;
import com.example.wallet.model.persistence.TenantPersistent;
import com.example.wallet.repository.TenantRepository;
import com.example.wallet.service.TenantService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TenantServiceImpl implements TenantService {

  @Autowired
  private TenantRepository tenantRepository;
  
  @Autowired
  private TenantConverter tenantConverter;

  @Autowired
  private PasswordEncoder passwordEncoder;

  Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);
 
  public Flux<TenantDTO> findAll(int offset, int limit) {
    return tenantRepository.findAllBy(PageRequest.of(offset, limit)).map(tenantConverter.persistentToDto());
  }

  public Flux<TenantDTO> findByLastName(String lastname, int offset, int limit) {
    return tenantRepository.findByLastname(lastname, PageRequest.of(offset, limit)).map(tenantConverter.persistentToDto());
  }

  public Mono<TenantDTO> findById(Long id) {
    return tenantRepository.findById(id).map(tenantConverter.persistentToDto());
  }

  public Mono<TenantDTO> insert(TenantDTO tenant) {
    TenantPersistent persistent = tenantConverter.dtoToPersistent().apply(tenant);
    persistent.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC))
        .setPassword(passwordEncoder.encode(tenant.getPassword()));
    
    return tenantRepository.findByNickname(persistent.getNickname())
        .doOnNext( t -> {
          throw new NickNameAlreadyExistsException(HttpStatus.BAD_REQUEST,
              String.format("Nickname already exists", tenant.getNickname()));
        })
        .switchIfEmpty(
            tenantRepository.findByEmail(persistent.getEmail())
        )
        .doOnNext(t -> {
            throw new EmailAlreadyExistsException(HttpStatus.BAD_REQUEST,
                  String.format("Email already exists", t.getEmail()));
        })
        .switchIfEmpty(tenantRepository.save(persistent))
        .flatMap(t -> Mono.just(tenantConverter.persistentToDto().apply(t)));
  }

  public Mono<TenantDTO> update(TenantDTO tenant) {
    TenantPersistent updated = tenantConverter.dtoToPersistent().apply(tenant);
    updated.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
    
    return tenantRepository.findById(tenant.getId())
        .switchIfEmpty(Mono.error(
            new EntityNotFoundException(HttpStatus.NOT_FOUND, String.format("Tenant %d not found", tenant.getId()))))
        .flatMap(persistent -> tenantRepository.save(updated.setPassword(persistent.getPassword()).setCreatedAt(persistent.getCreatedAt())))
        .flatMap(t -> Mono.just(tenantConverter.persistentToDto().apply(t)));
  }

  public Mono<Void> deleteById(Long id) {
    return tenantRepository.deleteById(id);
  }

}