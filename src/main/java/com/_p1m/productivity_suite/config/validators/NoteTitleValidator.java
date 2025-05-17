package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidNoteTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoteTitleValidator implements ConstraintValidator<ValidNoteTitle, String> {

    @Override
    public boolean isValid(final String title, final ConstraintValidatorContext context) {
        if (title.length() > 100)
            return buildViolation(context, "Note title must be less than 100 characters.");
        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
