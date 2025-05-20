package com._p1m.productivity_suite.data.enums;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public enum PriorityType {
    INVALID(0, "Invalid"),
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High");

    public final Integer value;
    public final String code;

    private PriorityType(Integer value, String code) {
        this.value = value;
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public static PriorityType fromInt(Integer value) {
        if (value == null) return INVALID;

        return Arrays.stream(PriorityType.values()).filter(x -> x.value.equals(value)).findFirst().orElse(INVALID);
    }

    public boolean isInvalid() {
        return this.value.equals(PriorityType.INVALID.getValue());
    }

    public boolean isLow() {
        return this.value.equals(PriorityType.LOW.getValue());
    }

    public boolean isMedium() {
        return this.value.equals(PriorityType.MEDIUM.getValue());
    }

    public boolean isHigh() {
        return this.value.equals(PriorityType.HIGH.getValue());
    }

    public static boolean isValidValue(Integer value) {
        return value != null && !fromInt(value).isInvalid();
    }


}
