package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTransactionDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TransactionDateValidator implements ConstraintValidator<ValidTransactionDate, Long> {


    @Override
    public boolean isValid(Long transactionDate, ConstraintValidatorContext context) {
        if(transactionDate == null){
            return buildViolation(context,"Transaction Date is required");
        }
        // Convert timestamp to LocalDateTime using system default timezone
        Instant transactionInstant = Instant.ofEpochMilli(transactionDate);
        ZonedDateTime transactionDateTime = transactionInstant.atZone(ZoneId.systemDefault());

        // Get current date and the start/end of the month
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
                .toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());

        if (transactionDateTime.isBefore(startOfMonth) || transactionDateTime.isAfter(endOfMonth)) {
            return buildViolation(context, "Transaction Date must be within the current month.");
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
