package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.data.enums.PomodoroActionType;

public record PomodoroResponse(Integer type, String remainingTime, Long timerId) {
	public static PomodoroResponse of(PomodoroActionType type,String time,Long id) {
		return new PomodoroResponse(type.getValue(),time,id);
	}
	public static PomodoroResponse invalid(String message) {
		return new PomodoroResponse(PomodoroActionType.INVALID.getValue(),message,null);
	}
}
