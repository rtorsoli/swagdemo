package com.example.wallet.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import com.example.wallet.model.response.LoginResponse;
import com.example.wallet.util.TokenInfo;


@Component
public class LoginConverter {

    private LoginConverter() {
        super();
    }

    public Function<TokenInfo, LoginResponse> toResponse() {
        return (TokenInfo tokenInfo) -> LoginResponse.builder()
                .tenantId(tokenInfo.getTenantId())
                .token(tokenInfo.getToken())
                .issuedAt(tokenInfo.getIssuedAt().toInstant().atOffset(ZoneOffset.UTC))
                .expiresAt(tokenInfo.getExpiresAt().toInstant().atOffset(ZoneOffset.UTC))
                .build();
    }
}
