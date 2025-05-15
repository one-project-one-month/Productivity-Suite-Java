package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.CategoryDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryDescriptionValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategoryDescription {
    String message() default "Invalid category description.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
