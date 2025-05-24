package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.TodoListDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TodoListDescriptionValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTodoListDescription {
    String message() default "Invalid todo-list description.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
