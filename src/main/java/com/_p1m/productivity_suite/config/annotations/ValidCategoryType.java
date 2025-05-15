package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.CategoryTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategoryType {
    String message() default "Category Type must be a number 1 (Pomodoro Timer), 2 (To-do List), or 3 (Budget Tracker)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
