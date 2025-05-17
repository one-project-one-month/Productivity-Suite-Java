package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidNoteBody;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoteBodyValidator implements ConstraintValidator<ValidNoteBody, String> {
    @Override
    public boolean isValid(final String body, final ConstraintValidatorContext context) {
        if (body == null || body.trim().isEmpty())
            return buildViolation(context, "Note body is required.");

        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
