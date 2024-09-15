package com.example.wallet.exception;

import org.springframework.http.HttpStatus;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class InvalidRequestException  extends RuntimeException {
    private HttpStatus httpStatus;
    private Set<String> errors;
}
