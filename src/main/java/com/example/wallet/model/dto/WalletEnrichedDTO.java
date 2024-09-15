package com.example.wallet.model.dto;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = false, chain = true)
@SuperBuilder
public class WalletEnrichedDTO extends WalletDTO {
        
    private BigDecimal price;

}
