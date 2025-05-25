package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.TodoListDueAtValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TodoListDueAtValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTodoListDueAt {
    String message() default "Invalid dueAt timestamp.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
