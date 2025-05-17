package com._p1m.productivity_suite.data.enums;

import java.util.Arrays;

public enum PomodoroActionType {
    INVALID(0, "Invalid"),
    TICK(1, "Tick"),
    RESET(2, "Reset"),
    STOP(3, "Stop"),
    COMPLETE(4, "Complete");

    private final Integer value;
    private final String code;

    PomodoroActionType(Integer value, String code) {
        this.value = value;
        this.code = code;
    }

    public Integer getValue() { return value; }

    public String getCode() { return code; }

    public static PomodoroActionType fromInt(Integer value) {
        if (value == null) return INVALID;
        return Arrays.stream(values())
                .filter(a -> a.value.equals(value))
                .findFirst()
                .orElse(INVALID);
    }

    public boolean isTick() { return this == TICK; }
    public boolean isReset() { return this == RESET; }
    public boolean isStop() { return this == STOP; }
    public boolean isComplete() { return this == COMPLETE; }
    public boolean isInvalid() { return this == INVALID; }

    public static boolean isValidValue(Integer value) {
        return value != null && !fromInt(value).isInvalid();
    }
}
