package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.data.enums.PomodoroActionType;

public record PomodoroWebSocketResponse(Integer type, String remainingTime, Long timerId) {
	public static PomodoroWebSocketResponse of(PomodoroActionType type,String time,Long id) {
		return new PomodoroWebSocketResponse(type.getValue(),time,id);
	}
	public static PomodoroWebSocketResponse invalid(String message) {
		return new PomodoroWebSocketResponse(PomodoroActionType.INVALID.getValue(),message,null);
	}
}
