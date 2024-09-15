package com.example.wallet.model.response;

import com.example.wallet.model.Response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = false, chain = true)
public class TenantResponse implements Response {
    
    private Long id;
    
    private String firstname;
    
    private String lastname;
    
    private String nickname;

    private String email;
}
