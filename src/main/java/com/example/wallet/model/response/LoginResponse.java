package com.example.wallet.model.response;

import com.example.wallet.model.Response;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse implements Response {
    
    private Long tenantId;
    private String token;
    private OffsetDateTime issuedAt;
    private OffsetDateTime expiresAt;
}
