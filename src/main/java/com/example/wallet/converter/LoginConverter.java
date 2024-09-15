package com.example.wallet.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.wallet.model.dto.LoginDTO;
import com.example.wallet.model.request.LoginRequest;
import com.example.wallet.model.response.LoginResponse;


@Component
public class LoginConverter {

    private LoginConverter() {
        super();
    }

    public Function<LoginDTO, LoginResponse> dtoToResponse() {
        return (LoginDTO Login) -> 
             LoginResponse.builder()
        .id(1L).build();
    }

    public Function<LoginRequest, LoginDTO> requestToDto() {
        return (LoginRequest Login) -> LoginDTO.builder()
                    .username(Login.getUsername())
                    .password(Login.getPassword()).build();
    }
}
