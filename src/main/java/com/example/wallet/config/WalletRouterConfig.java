package com.example.wallet.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.wallet.handler.LoginHandler;
import com.example.wallet.handler.TenantHandler;
import com.example.wallet.handler.WalletHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WalletRouterConfig {

    private final TenantHandler tenantHandler;
    private final WalletHandler walletHandler;
    private final LoginHandler loginHandler;

    public WalletRouterConfig(TenantHandler tenantHandler, WalletHandler walletHandler, LoginHandler loginHandler) {
        this.tenantHandler = tenantHandler;
        this.walletHandler = walletHandler;
        this.loginHandler = loginHandler;
    }

    @Bean
    RouterFunction<ServerResponse> tenantRouter() {
        return route().path("/api/v1/tenants", builder -> builder
                .GET("/", RequestPredicates.accept(MediaType.APPLICATION_JSON), tenantHandler::findAll)
                .GET("/lastname/{lastname}", RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        tenantHandler::findByLastName)
                .GET("/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), tenantHandler::findById)
                .PUT("/",
                        RequestPredicates.accept(MediaType.APPLICATION_JSON)
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        tenantHandler::update)
                .DELETE("/{id}", tenantHandler::delete)).build();
    }
    
    @Bean
    RouterFunction<ServerResponse> walletRouter() {
            return route().path("/api/v1/wallets", builder -> builder
                            .GET("/tenant/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON),
                                            walletHandler::findByTenantId)
                            .GET("/address/{address}", RequestPredicates.accept(MediaType.APPLICATION_JSON),
                                            walletHandler::findByAddress)
                            .GET("/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), walletHandler::findById)
                            .GET("/data/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON),
                                            walletHandler::findByIdWithData)
                            .POST("/", RequestPredicates.accept(MediaType.APPLICATION_JSON).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), walletHandler::insert)
                            .PUT("/",
                                            RequestPredicates.accept(MediaType.APPLICATION_JSON)
                                                            .and(RequestPredicates
                                                                            .contentType(MediaType.APPLICATION_JSON)),
                                                                            walletHandler::update)
                            .DELETE("/{id}", walletHandler::delete)).build();
    }
    
    @Bean
    RouterFunction<ServerResponse> publicRouter() {
        return route().path("/api/v1", builder -> builder
                .POST("/registration", RequestPredicates.accept(MediaType.APPLICATION_JSON).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), tenantHandler::registration)
                .POST("/login", RequestPredicates.accept(MediaType.APPLICATION_JSON).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), loginHandler::login)
        ).build();
    }
}