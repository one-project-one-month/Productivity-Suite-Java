package com._p1m.productivity_suite.data.enums;

import java.util.Arrays;

public enum CategoryType {
    INVALID(0, "Invalid"),
    POMODORO_TIMER(1, "Pomodoro Timer"),
    TO_DO_LIST(2, "To-do List"),
    BUDGET_TRACKER(3, "Budget Tracker");

    private final Integer value;
    private final String code;

    private CategoryType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }

    public Integer getValue() { return value; }

    public String getCode() { return code; }

    public static CategoryType fromInt(Integer value) {
        if (value == null) return INVALID;

        return Arrays.stream(CategoryType.values())
                .filter(gender -> gender.getValue().equals(value))
                .findFirst()
                .orElse(INVALID);
    }

    public boolean isInvalid() { return this.value.equals(CategoryType.INVALID.getValue()); }

    public boolean isPomodoroTimer() { return this.value.equals(CategoryType.POMODORO_TIMER.getValue()); }

    public boolean isToDoList() { return this.value.equals(CategoryType.TO_DO_LIST.getValue()); }

    public boolean isBudgetTracker() { return this.value.equals(CategoryType.BUDGET_TRACKER.getValue()); }

    public static boolean isValidValue(Integer value) {
        return value != null && !fromInt(value).isInvalid();
    }
}
