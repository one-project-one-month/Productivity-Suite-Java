package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.NoteTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoteTitleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNoteTitle {
    String message() default "Invalid note title.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
