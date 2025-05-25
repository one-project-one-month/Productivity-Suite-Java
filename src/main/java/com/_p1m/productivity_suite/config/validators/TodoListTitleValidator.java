package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTodoListTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TodoListTitleValidator implements ConstraintValidator<ValidTodoListTitle, String> {
    @Override
    public boolean isValid(final String todoListTitle, final ConstraintValidatorContext context) {
        if (todoListTitle == null || todoListTitle.trim().isEmpty()) {
            return buildViolation(context, "Todo-list Title is Required!");
        }
        if (todoListTitle.length() < 3 || todoListTitle.length() > 100) {
            return buildViolation(context, "Todo-list Title must be between 3 and 100 characters!");
        }
        return false;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
