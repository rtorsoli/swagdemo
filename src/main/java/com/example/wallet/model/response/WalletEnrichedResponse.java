package com.example.wallet.model.response;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Accessors(fluent = false, chain = true)
public class WalletEnrichedResponse extends WalletResponse {
    
    private BigDecimal price;

}
