package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTodoListCompletedAt;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TodoListCompletedAtValidator implements ConstraintValidator<ValidTodoListCompletedAt, Long> {
    @Override
    public boolean isValid(final Long completedAt, final ConstraintValidatorContext context) {
        if (completedAt == null) {
            return true;
        }
        final long now = System.currentTimeMillis();
        if (completedAt <= 0) {
            return buildViolation(context, "CompletedAt must be positive number");
        }
        if (completedAt > now) {
            return buildViolation(context, "CompletedAt cannot be in the future");
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
