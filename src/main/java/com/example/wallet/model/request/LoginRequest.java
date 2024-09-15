package com.example.wallet.model.request;

import com.example.wallet.model.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest implements Request {
        
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    @Size(max=255)
    private String username;
    
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(max=255)
    private String password;
    
}
