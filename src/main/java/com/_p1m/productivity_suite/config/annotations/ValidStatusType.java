package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.StatusTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatusType {
    String message() default "Priority Type must be a number 1 (To-do), 2 (In-progress), 3 (Waiting), 4 (Done), or 5 (Archived)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
