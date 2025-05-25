package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.PriorityTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriorityTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPriorityType {
    String message() default "Priority Type must be a number 1 (Low), 2 (Medium), or 3 (High)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
