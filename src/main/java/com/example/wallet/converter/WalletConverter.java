package com.example.wallet.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.wallet.model.dto.WalletDTO;
import com.example.wallet.model.persistence.WalletPersistent;
import com.example.wallet.model.request.WalletRequest;
import com.example.wallet.model.response.WalletResponse;


@Component
public class WalletConverter {

    private WalletConverter() {
        super();
    }

    public Function<WalletDTO, WalletResponse> dtoToResponse() {
        return (WalletDTO wallet) -> 
             WalletResponse.builder()
        .id(wallet.getId())
        .name(wallet.getName())
        .description(wallet.getDescription())
        .address(wallet.getAddress())
        .crypto(wallet.getCrypto())
        .currency(wallet.getCurrency())
        .tenantId(wallet.getTenantId())
        .quantity(wallet.getQuantity())
        .createdAt(wallet.getCreatedAt())
                     .build();
    }

    public Function<WalletPersistent, WalletDTO> persistentToDto() {
        
        return (WalletPersistent wallet) ->  
        
            WalletDTO.builder()
            .id(wallet.getId())
            .name(wallet.getName())
            .description(wallet.getDescription())
            .address(wallet.getAddress())
            .crypto(wallet.getCrypto())
            .currency(wallet.getCurrency())
            .tenantId(wallet.getTenantId())
            .quantity(wallet.getQuantity())
            .createdAt(wallet.getCreatedAt())
            .build();
        
    }

    public Function<WalletDTO, WalletPersistent> dtoToPersistent() {
        return (WalletDTO wallet) -> WalletPersistent.builder()
        .id(wallet.getId())
            .name(wallet.getName())
            .description(wallet.getDescription())
            .address(wallet.getAddress())
            .crypto(wallet.getCrypto())
            .currency(wallet.getCurrency())
            .tenantId(wallet.getTenantId())
            .quantity(wallet.getQuantity())
            .build();
    }

    public Function<WalletRequest, WalletDTO> requestToDto() {
        return (WalletRequest wallet) -> WalletDTO.builder().id(wallet.getId())
        .id(wallet.getId())
            .name(wallet.getName())
            .description(wallet.getDescription())
            .address(wallet.getAddress())
            .crypto(wallet.getCrypto())
            .currency(wallet.getCurrency())
            .tenantId(wallet.getTenantId())
            .quantity(wallet.getQuantity())
            .build();
    }
}
