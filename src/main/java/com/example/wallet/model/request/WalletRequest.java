package com.example.wallet.model.request;

import java.math.BigDecimal;

import com.example.wallet.model.Request;
import com.example.wallet.model.enumeration.CoinEnum;
import com.example.wallet.model.enumeration.CurrencyEnum;
import com.example.wallet.validator.Registration;
import com.example.wallet.validator.Updated;
import com.example.wallet.validator.ValidAddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = false, chain = true)
@ValidAddress(message = "Address must be valid", groups = { Registration.class})
public class WalletRequest implements Request {
    
    @NotNull(message = "Id is mandatory", groups = Updated.class)
    private Long id;

    @NotNull(message = "Address is mandatory", groups = { Registration.class})
    @NotBlank(message = "Address is mandatory", groups = { Registration.class})
    private String address;
    
    @NotNull(message = "Crypto symbol is mandatory", groups = { Registration.class })
    private CoinEnum crypto;
  
    @NotNull(message = "Name is mandatory", groups = { Registration.class, Updated.class })
    @NotBlank(message = "Name is mandatory", groups = { Registration.class, Updated.class })
    @Size(max=255, groups = { Registration.class, Updated.class })
    private String name;
    
    @NotNull(message = "Description is mandatory", groups = { Registration.class, Updated.class })
    @NotBlank(message = "Description is mandatory", groups = { Registration.class, Updated.class })
    @Size(max=255, groups = { Registration.class, Updated.class })
    private String description;

    @NotNull(message = "Currency is mandatory", groups = { Registration.class})
    private CurrencyEnum currency;
    
    @NotNull(message = "Quantity is mandatory", groups = { Registration.class})
    private BigDecimal quantity;

    @NotNull(message = "Tenant is mandatory", groups = { Registration.class})
    private Long tenantId;
}
