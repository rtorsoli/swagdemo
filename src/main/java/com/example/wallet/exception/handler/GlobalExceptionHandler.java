package com.example.wallet.exception.handler;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import com.example.wallet.exception.AddressAlreadyExistsException;
import com.example.wallet.exception.EmailAlreadyExistsException;
import com.example.wallet.exception.EntityNotFoundException;
import com.example.wallet.exception.InvalidLoginException;
import com.example.wallet.exception.InvalidParamException;
import com.example.wallet.exception.InvalidRequestException;
import com.example.wallet.exception.MalformedRequestException;
import com.example.wallet.exception.NickNameAlreadyExistsException;
import com.example.wallet.exception.TenantNotFoundException;


@RestControllerAdvice
@Order(-2)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidParamException.class)
    protected ProblemDetail handleValidation(InvalidParamException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setTitle("Invalid parameter");
        problemDetail.setProperty("errors", List.of(ex.getParameter() + " is not valid"));
        return problemDetail;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ProblemDetail handleValidation(EntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Entity not found");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        return problemDetail;
    }

    @ExceptionHandler(TenantNotFoundException.class)
    protected ProblemDetail handleValidation(TenantNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Tenant not found");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        return problemDetail;
    }

    @ExceptionHandler(MalformedRequestException.class)
    protected ProblemDetail handleValidation(MalformedRequestException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
        problemDetail.setTitle("Request is empty");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        return problemDetail;
    }

    @ExceptionHandler(InvalidRequestException.class)
    protected ProblemDetail handleValidation(InvalidRequestException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
        problemDetail.setTitle("Request is not valid");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getErrors()));
        return problemDetail;
    }

    @ExceptionHandler(NickNameAlreadyExistsException.class)
    protected ProblemDetail handleValidation(NickNameAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
        problemDetail.setTitle("Request is not valid");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        return problemDetail;
    }

    
    @ExceptionHandler(AddressAlreadyExistsException.class)
    protected ProblemDetail handleValidation(AddressAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
        problemDetail.setTitle("Request is not valid");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        return problemDetail;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ProblemDetail handleValidation(EmailAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
        problemDetail.setTitle("Request is not valid");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        return problemDetail;
    }

    @ExceptionHandler(InvalidLoginException.class)
    protected ProblemDetail handleValidation(InvalidLoginException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
        problemDetail.setTitle("Login is not valid");
        problemDetail.setStatus(ex.getHttpStatus());
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        return problemDetail;
    }
}