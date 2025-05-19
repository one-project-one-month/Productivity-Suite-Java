package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTransactionAmount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TransactionAmountValidator implements ConstraintValidator<ValidTransactionAmount, BigDecimal> {

    @Override
    public boolean isValid(final BigDecimal transactionAmount, final ConstraintValidatorContext context) {
       if(transactionAmount == null ){
           return buildViolation(context,"Transaction Value is required.");
       }
        try {
            // Ensure amount is a whole number
            BigInteger amount = transactionAmount.toBigIntegerExact();

            if (amount.compareTo(BigInteger.ONE) < 0 || amount.compareTo(BigInteger.valueOf(999999)) > 0) {
                return buildViolation(context, "Transaction Amount must be between 1 and 999999.");
            }
        } catch (ArithmeticException e) {
            return buildViolation(context, "Transaction Amount must be a whole number.");
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
