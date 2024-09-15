package com.example.wallet.model.persistence;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.wallet.model.Persistent;
import com.example.wallet.model.enumeration.CoinEnum;
import com.example.wallet.model.enumeration.CurrencyEnum;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Table(name = "wallets")
@Accessors(fluent = false, chain = true)
public class WalletPersistent implements Persistent {
    
    @Id
    private Long id;
    
    private String name;
    
    private String description;

    private String address;
    
    private CoinEnum crypto;

    private CurrencyEnum currency;

    private BigDecimal quantity;
    
    @Column("TENANTID")
    private Long tenantId;

    @Column("CREATEDAT")
    private OffsetDateTime createdAt;
}
