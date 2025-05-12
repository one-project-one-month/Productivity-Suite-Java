package com._p1m.productivity_suite.data.enums;

import java.util.Arrays;

public enum TimerType {
    INVALID(0, "Invalid"),
    POMODORO(1, "Pomodoro"),
    SHORT_BREAK(2, "Short Break"),
    LONG_BREAK(3, "Long Break");

    private final Integer value;
    private final String code;

    private TimerType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public static TimerType fromInt(Integer value) {
        if (value == null) return INVALID;

        return Arrays.stream(TimerType.values())
                .filter(type -> type.getValue().equals(value))
                .findFirst()
                .orElse(INVALID);
    }

    public boolean isInvalid() {
        return this == INVALID;
    }

    public boolean isPomodoro() {
        return this == POMODORO;
    }

    public boolean isShortBreak() {
        return this == SHORT_BREAK;
    }

    public boolean isLongBreak() {
        return this == LONG_BREAK;
    }

    public static boolean isValidValue(Integer value) {
        return value != null && !fromInt(value).isInvalid();
    }
}
