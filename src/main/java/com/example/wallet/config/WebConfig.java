package com.example.wallet.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebConfig {
         
    @Value("${rapidapi.host}")
    private String rapidapiHost;
    
    @Value("${rapidapi.key}")
    private String rapidapiKey;

    @Bean
    WebClient webClientCrypto() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                        .responseTimeout(Duration.ofMillis(3000))
                        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(3000, TimeUnit.MILLISECONDS)))))
                .defaultHeader("x-rapidapi-host", rapidapiHost)
                .defaultHeader("x-rapidapi-key", rapidapiKey)
                .build();
    }
}
