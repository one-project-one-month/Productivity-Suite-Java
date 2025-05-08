package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public boolean isValid(final String name, final ConstraintValidatorContext context) {
        if (name == null || name.trim().isEmpty()) {
            return buildViolation(context, "Name is required.");
        }

        if (name.length() < 3 || name.length() > 50) {
            return buildViolation(context, "Name must be between 3 and 50 characters.");
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
