package com.example.wallet.model.dto;

import com.example.wallet.model.DTO;
import com.example.wallet.model.enumeration.CoinEnum;
import com.example.wallet.model.enumeration.CurrencyEnum;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Accessors(fluent = false, chain = true)
public class WalletDTO implements DTO {
    
    private Long id;
        
    private String address;
    
    private CoinEnum crypto;
    
    private String name;
    
    private String description;

    private CurrencyEnum currency;
    
    private BigDecimal quantity;
    
    private Long tenantId;

    private OffsetDateTime createdAt;
}
