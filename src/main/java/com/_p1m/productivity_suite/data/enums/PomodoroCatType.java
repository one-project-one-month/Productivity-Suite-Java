package com._p1m.productivity_suite.data.enums;

import java.util.Arrays;

public enum PomodoroCatType {
	INVALID(0, "Invalid"), WORK(1, "Work"), STUDY(2, "Study"), PERSONAL(3, "Personal");

	private final Integer value;
	private final String code;

	PomodoroCatType(Integer value, String code) {
		this.value = value;
		this.code = code;
	}

	public Integer getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

	public static PomodoroCatType fromInt(Integer value) {
		if (value == null)
			return INVALID;
		return Arrays.stream(values()).filter(a -> a.value.equals(value)).findFirst().orElse(INVALID);
	}

	public boolean isTick() {
		return this == WORK;
	}

	public boolean isReset() {
		return this == STUDY;
	}

	public boolean isStop() {
		return this == PERSONAL;
	}

	public boolean isInvalid() {
		return this == INVALID;
	}

	public static boolean isValidValue(Integer value) {
		return value != null && !fromInt(value).isInvalid();
	}
}
