package com.example.wallet.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import com.example.wallet.converter.WalletConverter;
import com.example.wallet.exception.EntityNotFoundException;
import com.example.wallet.exception.InvalidParamException;
import com.example.wallet.exception.InvalidRequestException;
import com.example.wallet.exception.MalformedRequestException;
import com.example.wallet.handler.WalletHandler;
import com.example.wallet.model.request.WalletRequest;
import com.example.wallet.model.response.TenantResponse;
import com.example.wallet.service.WalletService;
import com.example.wallet.validator.Registration;
import com.example.wallet.validator.Updated;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WalletHandlerImpl implements WalletHandler {

    private final WalletService walletService;

    private final WalletConverter walletConverter;
   
    private final Validator walletValidator;

    Logger logger = LoggerFactory.getLogger(WalletHandlerImpl.class);
    
    public WalletHandlerImpl(WalletService walletService,
            WalletConverter walletConverter, Validator walletValidator) {
        this.walletService = walletService;
        this.walletConverter = walletConverter;
        this.walletValidator = walletValidator;
    }

    @Override
    public Mono<ServerResponse> findByAddress(final ServerRequest request) {
        return Mono.just(request.pathVariable("address"))
                .flatMap(address -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(walletService.findByAddress(address).map(walletConverter.dtoToResponse()),
                                TenantResponse.class));
    }
    
    @Override
    public Mono<ServerResponse> findByTenantId(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .map(Long::parseLong)
                .doOnError(ex -> {
                    throw new InvalidParamException(HttpStatus.BAD_REQUEST,"id");
                })
                .flatMap(id -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(walletService.findByTenantId(id).map(walletConverter.dtoToResponse()),TenantResponse.class));
    }
    
    @Override
    public Mono<ServerResponse> findById(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .map(Long::parseLong)
                .doOnError(ex -> {
                    throw new InvalidParamException(HttpStatus.BAD_REQUEST, "id");
                })
                .flatMap(id -> walletService.findById(id))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(HttpStatus.NOT_FOUND, "Wallet not found")))
                .flatMap(wallet -> ServerResponse
                        .ok()
                        .bodyValue(walletConverter.dtoToResponse().apply(wallet)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
    
    @Override
    public Mono<ServerResponse> findByIdWithData(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .map(Long::parseLong)
                .doOnError(ex -> {
                    throw new InvalidParamException(HttpStatus.BAD_REQUEST,"id");
                })
                .flatMap(id -> walletService.findByIdEnriched(id))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(HttpStatus.NOT_FOUND, "Wallet not found")))
                .flatMap(wallet -> ServerResponse
                        .ok()
                        .bodyValue(walletConverter.dtoToEnrichResponse().apply(wallet)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> insert(final ServerRequest request) {
        
        return request.bodyToMono(WalletRequest.class)
                .switchIfEmpty(
                        Mono.error(new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body not found")))
                .doOnNext( 
                        wallet -> {
                            try {
                                Set<ConstraintViolation<WalletRequest>> errors = walletValidator.validate(wallet, Registration.class);
                                if (!errors.isEmpty()) {
                                    throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                                            errors.stream().map(e -> e.getMessage()).collect(Collectors.toSet()));
                                }
                            } catch (ValidationException ex) {
                                logger.info(ex.getMessage());
                                throw new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body is not valid");
                            }
                        }
                )
                .map(walletConverter.requestToDto())
                .flatMap(wallet -> walletService.insert(wallet))
                .flatMap(wallet -> ServerResponse
                        .ok()
                        .bodyValue(walletConverter.dtoToResponse().apply(wallet)));
    }

    @Override
    public Mono<ServerResponse> update(final ServerRequest request) {
        return request.bodyToMono(WalletRequest.class)
                .switchIfEmpty(Mono.error(new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body not found")))
                .doOnNext(
                        wallet -> {
                            try {
                                Set<ConstraintViolation<WalletRequest>> errors = walletValidator.validate(wallet, Updated.class);
                                if (!errors.isEmpty()) {
                                    throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                                            errors.stream().map(e -> e.getMessage()).collect(Collectors.toSet()));
                                }
                            } catch (ValidationException ex) {
                                throw new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body is not valid");
                            }
                        }
                )
                .map(walletConverter.requestToDto())
                .flatMap(wallet -> walletService.update(wallet))
                .flatMap(wallet -> ServerResponse
                        .ok()
                        .bodyValue(walletConverter.dtoToResponse().apply(wallet)));
    }

    @Override
    public Mono<ServerResponse> delete(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .map(Long::parseLong)
                .doOnError(ex -> {
                    throw new InvalidParamException(HttpStatus.BAD_REQUEST, "id");
                })
                .flatMap(id -> walletService.deleteById(id))
                .flatMap(none -> ServerResponse
                        .noContent().build());
    }
}


 
