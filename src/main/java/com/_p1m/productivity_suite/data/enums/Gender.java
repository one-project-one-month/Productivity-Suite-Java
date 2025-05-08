package com._p1m.productivity_suite.data.enums;

import java.util.Arrays;

public enum Gender {
    INVALID(0, "Invalid"),
    MALE(1, "Male"),
    FEMALE(2, "Female"),
    OTHER(3, "Other");

    private final Integer value;
    private final String code;

    private Gender(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }

    public Integer getValue() { return value; }

    public String getCode() { return code; }

    public static Gender fromInt(Integer value) {
        if (value == null) return INVALID;

        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getValue().equals(value))
                .findFirst()
                .orElse(INVALID);
    }

    public boolean isInvalid() { return this.value.equals(Gender.INVALID.getValue()); }

    public boolean isMale() { return this.value.equals(Gender.MALE.getValue()); }

    public boolean isFemale() { return this.value.equals(Gender.FEMALE.getValue()); }

    public boolean isOther() { return this.value.equals(Gender.OTHER.getValue()); }

    public static boolean isValidValue(Integer value) {
        return value != null && !fromInt(value).isInvalid();
    }
}
