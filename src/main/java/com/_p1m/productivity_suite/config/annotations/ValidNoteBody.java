package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.NoteBodyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoteBodyValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNoteBody {
    String message() default "Invalid note body.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
