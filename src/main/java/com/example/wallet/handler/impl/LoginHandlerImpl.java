package com.example.wallet.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import com.example.wallet.converter.LoginConverter;
import com.example.wallet.exception.InvalidRequestException;
import com.example.wallet.exception.MalformedRequestException;
import com.example.wallet.handler.LoginHandler;
import com.example.wallet.model.request.LoginRequest;
import com.example.wallet.service.LoginService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginHandlerImpl implements LoginHandler {

    private final LoginService loginService;

    private final LoginConverter loginConverter;
   
    private final Validator loginValidator;

    Logger logger = LoggerFactory.getLogger(LoginHandlerImpl.class);
    
    public LoginHandlerImpl(LoginService loginService,
    LoginConverter loginConverter, Validator loginValidator) {
        this.loginService = loginService;
        this.loginConverter = loginConverter;
        this.loginValidator = loginValidator;
    }

    public Mono<ServerResponse> login(final ServerRequest request) {
        
        return request.bodyToMono(LoginRequest.class)
                .switchIfEmpty(
                        Mono.error(new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body not found")))
                .doOnNext( 
                        Login -> {
                            try {
                                Set<ConstraintViolation<LoginRequest>> errors = loginValidator.validate(Login);
                                if (!errors.isEmpty()) {
                                    throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                                            errors.stream().map(e -> e.getMessage()).collect(Collectors.toSet()));
                                }
                            } catch (ValidationException ex) {
                                throw new MalformedRequestException(HttpStatus.BAD_REQUEST, "Request body is not valid");
                            }
                        }
                )
                .map(loginConverter.requestToDto())
                .flatMap(login -> loginService.login(login))
                .flatMap(login -> ServerResponse
                        .ok()
                        .bodyValue(loginConverter.dtoToResponse()));
    }
}


 
