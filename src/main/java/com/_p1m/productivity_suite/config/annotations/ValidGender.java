package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.GenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {
    String message() default "Gender must be a number between 1 (Male), 2 (Female), or 3 (Other).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
