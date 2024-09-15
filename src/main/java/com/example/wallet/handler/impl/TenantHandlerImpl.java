package com.example.wallet.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import com.example.wallet.converter.TenantConverter;
import com.example.wallet.exception.EntityNotFoundException;
import com.example.wallet.exception.InvalidParamException;
import com.example.wallet.exception.InvalidRequestException;
import com.example.wallet.exception.MalformedRequestException;
import com.example.wallet.handler.TenantHandler;
import com.example.wallet.model.request.TenantRequest;
import com.example.wallet.model.response.TenantResponse;
import com.example.wallet.service.TenantService;
import com.example.wallet.validator.Registration;
import com.example.wallet.validator.Updated;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TenantHandlerImpl implements TenantHandler {

    private final TenantService tenantService;

    private final TenantConverter tenantConverter;
   
    private final Validator tenantValidator;

    Logger logger = LoggerFactory.getLogger(TenantHandlerImpl.class);
    
    public TenantHandlerImpl(TenantService tenantService,
            TenantConverter tenantConverter, Validator tenantValidator) {
        this.tenantService = tenantService;
        this.tenantConverter = tenantConverter;
        this.tenantValidator = tenantValidator;
    }

    private Tuple2<Integer, Integer> getPageParameters( final ServerRequest request) {
        int offset = request.queryParam("offset")
                .map(input -> {
                    try {
                        return Integer.parseInt(input);
                    } catch (NumberFormatException ex) {
                        return 0;
                    }
                }).orElse(0);
        int limit = request.queryParam("limit")
                .map(input -> {
                    try {
                        return Integer.parseInt(input);
                    } catch (NumberFormatException ex) {
                        return 10;
                    }
                })
                .orElse(10);
        return Tuples.of(offset, limit);
    }

    public Mono<ServerResponse> findAll(final ServerRequest request) {
        Tuple2<Integer, Integer> page = getPageParameters(request);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tenantService
                        .findAll(page.getT1(), page.getT2())
                        .map(tenantConverter.dtoToResponse()),
                        TenantResponse.class);
    }

    public Mono<ServerResponse> findByLastName(final ServerRequest request) {
        Tuple2<Integer, Integer> page = getPageParameters(request);
        String lastname = request.pathVariable("lastname");

        return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(tenantService
                .findByLastName(lastname, page.getT1(), page.getT2())
                .map(tenantConverter.dtoToResponse()),
                TenantResponse.class);
    }

    public Mono<ServerResponse> findById(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .map(Long::parseLong)
                .doOnError(ex -> {
                    throw new InvalidParamException(HttpStatus.BAD_REQUEST,"id");
                })
                .flatMap(id -> tenantService.findById(id))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(HttpStatus.NOT_FOUND, "Tenant not found")))
                .flatMap(tenant -> ServerResponse
                        .ok()
                        .bodyValue(tenantConverter.dtoToResponse().apply(tenant)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> registration(final ServerRequest request) {
        
        return request.bodyToMono(TenantRequest.class)
                .switchIfEmpty(
                        Mono.error(new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body not found")))
                .doOnNext( 
                        tenant -> {
                            try {
                                Set<ConstraintViolation<TenantRequest>> errors = tenantValidator.validate(tenant, Registration.class);
                                if (!errors.isEmpty()) {
                                    throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                                            errors.stream().map(e -> e.getMessage()).collect(Collectors.toSet()));
                                }
                            } catch (ValidationException ex) {
                                throw new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body is not valid");
                            }
                        }
                )
                .map(tenantConverter.requestToDto())
                .flatMap(tenant -> tenantService.insert(tenant))
                .flatMap(tenant -> ServerResponse
                        .ok()
                        .bodyValue(tenantConverter.dtoToResponse().apply(tenant)));
    }

    public Mono<ServerResponse> update(final ServerRequest request) {
        return request.bodyToMono(TenantRequest.class)
                .switchIfEmpty(Mono.error(new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body not found")))
                .doOnNext(
                        tenant -> {
                            try {
                                Set<ConstraintViolation<TenantRequest>> errors = tenantValidator.validate(tenant, Updated.class);
                                if (!errors.isEmpty()) {
                                    throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                                            errors.stream().map(e -> e.getMessage()).collect(Collectors.toSet()));
                                }
                            } catch (ValidationException ex) {
                                throw new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body is not valid");
                            }
                        }
                )
                .map(tenantConverter.requestToDto())
                .flatMap(tenant -> tenantService.update(tenant))
                .flatMap(tenant -> ServerResponse
                        .ok()
                        .bodyValue(tenantConverter.dtoToResponse().apply(tenant)));
    }

    public Mono<ServerResponse> delete(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .map(Long::parseLong)
                .doOnError(ex -> {
                    throw new InvalidParamException(HttpStatus.BAD_REQUEST, "id");
                })
                .flatMap(id -> tenantService.deleteById(id))
                .flatMap(none -> ServerResponse
                        .noContent().build());
    }
}


 
