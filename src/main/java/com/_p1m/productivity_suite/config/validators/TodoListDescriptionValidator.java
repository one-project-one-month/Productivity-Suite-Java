package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTodoListDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TodoListDescriptionValidator implements ConstraintValidator<ValidTodoListDescription, String> {

    @Override
    public boolean isValid(final String todoListDescription, final ConstraintValidatorContext context) {
        if (todoListDescription == null || todoListDescription.trim().isEmpty()) {
            return buildViolation(context, "Todo-list Description is required!");
        }
        if (todoListDescription.length() < 3 || todoListDescription.length() > 100) {
            return buildViolation(context, "Todo-list Description must be between 3 and 100 characters!");
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
