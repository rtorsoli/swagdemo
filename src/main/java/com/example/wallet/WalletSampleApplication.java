package com.example.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@EnableWebFlux
@EnableR2dbcRepositories
@SpringBootApplication
@OpenAPIDefinition
public class WalletSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletSampleApplication.class, args);
	}
}
