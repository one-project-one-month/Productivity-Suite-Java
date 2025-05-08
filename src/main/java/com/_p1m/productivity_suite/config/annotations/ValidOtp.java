package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.OtpValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OtpValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOtp {
    String message() default "OTP must be a 6-digit number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
