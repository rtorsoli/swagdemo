package com.example.wallet.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.wallet.handler.LoginHandler;
import com.example.wallet.handler.TenantHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TenantRouterConfig {

    private final TenantHandler tenantHandler;
    private final LoginHandler loginHandler;

    public TenantRouterConfig(TenantHandler tenantHandler, LoginHandler loginHandler) {
        this.tenantHandler = tenantHandler;
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
    RouterFunction<ServerResponse> publicRouter() {
        return route().path("/api/v1", builder -> builder
                .POST("/registration", RequestPredicates.accept(MediaType.APPLICATION_JSON).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), tenantHandler::registration)
                .POST("/login", RequestPredicates.accept(MediaType.APPLICATION_JSON).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), loginHandler::login)
        ).build();
    }
}