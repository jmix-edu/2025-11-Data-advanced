package com.company.jmixpmdata.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidDateProjectValidator.class)
public @interface ValidDateProject {

    String message() default "Start date must me before end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
