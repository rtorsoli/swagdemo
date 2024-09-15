package com.example.wallet.model.response;

import com.example.wallet.model.Response;
import com.example.wallet.model.enumeration.CoinEnum;
import com.example.wallet.model.enumeration.CurrencyEnum;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = false, chain = true)
public class WalletResponse implements Response {
    
    private Long id;
    
    private String address;

    private String name;
    
    private String description;
    
    private CoinEnum crypto;
    
    private CurrencyEnum currency;

    private BigDecimal quantity;
        
    private Long tenantId;

    private OffsetDateTime createdAt;
}
