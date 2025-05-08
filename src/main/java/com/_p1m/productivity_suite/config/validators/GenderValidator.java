package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidGender;
import com._p1m.productivity_suite.data.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, Integer> {

    public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
        return Gender.isValidValue(value);
    }
}
