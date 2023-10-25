package com.metrodataacademy.domain.dto.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortTypeValidator.class)
public @interface ValidSortType {
    String message() default "Invalid content type must be video";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
