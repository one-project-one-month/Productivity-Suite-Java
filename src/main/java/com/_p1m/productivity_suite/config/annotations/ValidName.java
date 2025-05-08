package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Invalid name.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
