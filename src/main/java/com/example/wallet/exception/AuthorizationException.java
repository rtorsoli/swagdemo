package com.example.wallet.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AuthorizationException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
