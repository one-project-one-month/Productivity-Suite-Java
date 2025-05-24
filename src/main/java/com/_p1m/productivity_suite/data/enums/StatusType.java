package com._p1m.productivity_suite.data.enums;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public enum StatusType{
    INVALID(0, "Invalid"),
    TODO(1, "To-do"),
    IN_PROGRESS(2, "In-progress"),
    WAITING(3, "Waiting"),
    DONE(4, "Done"),
    ARCHIVED(5, "Archived");

    private final Integer value;
    private final String  code;

    private StatusType(Integer value, String code) {
        this.value = value;
        this.code  = code;
    }

    public Integer getValue() { return value; }
    public String  getCode()  { return code;  }

    /* -------- static helpers -------- */

    public static StatusType fromInt(Integer value) {
        if (value == null) return INVALID;
        return Arrays.stream(values())
                .filter(s -> s.value.equals(value))
                .findFirst()
                .orElse(INVALID);
    }

    public boolean isInvalid() {
        return this.value.equals(StatusType.INVALID.getValue());
    }

    public boolean isTodo() {
        return this.value.equals(StatusType.TODO.getValue());
    }

    public boolean isInProgress() {
        return this.value.equals(StatusType.IN_PROGRESS.getValue());
    }

    public boolean isWaiting() {
        return this.value.equals(StatusType.WAITING.getValue());
    }

    public boolean isDone(){
        return this.value.equals(StatusType.DONE.getValue());
    }

    public boolean isArchived() {
        return this.value.equals(StatusType.ARCHIVED.getValue());
    }

    public static boolean isValidValue(Integer value) {
        return value != null && !fromInt(value).isInvalid();
    }

}
