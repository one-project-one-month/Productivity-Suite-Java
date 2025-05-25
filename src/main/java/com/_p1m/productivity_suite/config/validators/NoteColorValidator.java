package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidNoteColor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class NoteColorValidator implements ConstraintValidator<ValidNoteColor, String> {

    @Override
    public boolean isValid(final String color, final ConstraintValidatorContext context) {
        return Optional.ofNullable(color)
                .map(String::trim)
                .filter(c -> !c.isEmpty())
                .map(c -> true)
                .orElseGet(() -> buildViolation(context, "Note color is required."));
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
