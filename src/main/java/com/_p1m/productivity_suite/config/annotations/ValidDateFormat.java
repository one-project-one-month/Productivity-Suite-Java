package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.DateFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFormat {
    String message() default "Invalid date format.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
