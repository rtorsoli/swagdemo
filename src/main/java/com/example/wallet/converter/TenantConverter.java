package com.example.wallet.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.wallet.model.dto.TenantDTO;
import com.example.wallet.model.persistence.TenantPersistent;
import com.example.wallet.model.request.TenantRequest;
import com.example.wallet.model.response.TenantResponse;


@Component
public class TenantConverter implements Converter<TenantRequest, TenantDTO, TenantPersistent, TenantResponse>{

    private TenantConverter() {
        super();
    }

    public Function<TenantDTO, TenantResponse> dtoToResponse() {
        return (TenantDTO tenant) -> 
             TenantResponse.builder()
        .id(tenant.getId())
        .firstname(tenant.getFirstname())
        .lastname(tenant.getLastname())
        .nickname(tenant.getNickname())
                    .email(tenant.getEmail()).build();
    }

    public Function<TenantPersistent, TenantDTO> persistentToDto() {
        return (TenantPersistent tenant) -> 
            TenantDTO.builder()
            .id(tenant.getId())
            .firstname(tenant.getFirstname())
            .lastname(tenant.getLastname())
            .nickname(tenant.getNickname())
                    .email(tenant.getEmail())
                    .password(tenant.getPassword())
            .build();
    }

    public Function<TenantDTO, TenantPersistent> dtoToPersistent() {
        return (TenantDTO tenant) -> TenantPersistent.builder()
                    .id(tenant.getId())
                    .firstname(tenant.getFirstname())
                    .lastname(tenant.getLastname())
                    .nickname(tenant.getNickname())
                    .password(tenant.getPassword())
                    .email(tenant.getEmail())
                    .build();
    }

    public Function<TenantRequest, TenantDTO> requestToDto() {
        return (TenantRequest tenant) -> TenantDTO.builder().id(tenant.getId())
                    .firstname(tenant.getFirstname())
                    .lastname(tenant.getLastname())
                    .nickname(tenant.getNickname())
                    .password(tenant.getPassword())
                    .email(tenant.getEmail()).build();
    }
}
