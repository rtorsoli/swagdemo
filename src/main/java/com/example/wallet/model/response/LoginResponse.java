package com.example.wallet.model.response;

import com.example.wallet.model.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse implements Response {
    
    private Long id;
}
