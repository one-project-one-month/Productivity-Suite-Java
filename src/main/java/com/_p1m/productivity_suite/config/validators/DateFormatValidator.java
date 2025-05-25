package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidDateFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {

    @Override
    public boolean isValid(String format, ConstraintValidatorContext context) {
        if (format == null || format.trim().isEmpty()) {
            return buildViolation(context, "Date format is required.");
        }

        try {
            DateTimeFormatter.ofPattern(format);
            return true;
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return this.buildViolation(context, "Invalid date format pattern.");
        }
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
        return false;
    }
}
