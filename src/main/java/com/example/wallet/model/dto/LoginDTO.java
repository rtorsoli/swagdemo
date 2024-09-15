package com.example.wallet.model.dto;

import com.example.wallet.model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO implements DTO {
    
    private String username;
    private String password;
}
