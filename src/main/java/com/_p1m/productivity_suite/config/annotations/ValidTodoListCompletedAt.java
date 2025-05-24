package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.TodoListCompletedAtValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TodoListCompletedAtValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTodoListCompletedAt {
    String message() default "Invalid completedAt timestamp.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
