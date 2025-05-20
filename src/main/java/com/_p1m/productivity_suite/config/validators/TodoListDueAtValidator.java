package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTodoListDueAt;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TodoListDueAtValidator implements ConstraintValidator<ValidTodoListDueAt, Long> {
    @Override
    public boolean isValid(final Long dueAt, final ConstraintValidatorContext context) {
        if (dueAt == null) {
            return true;
        }
        final long now = System.currentTimeMillis();
        if (dueAt <= 0) {
            return buildViolation(context, "DueAt must be positive number");
        }
        if (dueAt <= now) {
            return buildViolation(context, "DueAt must be set to a future date");
        }
        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
