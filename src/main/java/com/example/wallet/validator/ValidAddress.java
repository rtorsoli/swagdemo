package com.example.wallet.validator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ValidAddressValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface ValidAddress {
    String message() default "Address must be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
