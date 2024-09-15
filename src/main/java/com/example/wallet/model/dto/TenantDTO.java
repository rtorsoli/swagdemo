package com.example.wallet.model.dto;

import com.example.wallet.model.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = false, chain = true)
public class TenantDTO implements DTO {
    
    private Long id;
    private String firstname;
    private String lastname;
    private String nickname;
    private String password;
    private String email;
}
