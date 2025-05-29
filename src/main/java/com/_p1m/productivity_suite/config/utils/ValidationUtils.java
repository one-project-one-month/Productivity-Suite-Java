package com._p1m.productivity_suite.config.utils;

import java.util.Optional;

public final class ValidationUtils {

    private ValidationUtils() {}

    public static void requireNonNull(final Object value, final String fieldName) {
        Optional.ofNullable(value)
                .orElseThrow(() -> new IllegalArgumentException(fieldName + " must not be null"));
    }
}
