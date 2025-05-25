package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.TodoListTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TodoListTitleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTodoListTitle {
    String message() default "Invalid todo-list title.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
