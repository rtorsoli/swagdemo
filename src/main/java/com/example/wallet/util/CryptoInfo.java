package com.example.wallet.util;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoInfo {
    private String symbol;
    private BigDecimal rate;
    private String to_fiat;
}
