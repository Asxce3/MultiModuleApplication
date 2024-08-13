package com.example.UserService.annotations.validation.telephone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneRegionValidator.class)
public @interface ValidPhoneRegion {
    String message() default "Invalid phone and region combination";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
