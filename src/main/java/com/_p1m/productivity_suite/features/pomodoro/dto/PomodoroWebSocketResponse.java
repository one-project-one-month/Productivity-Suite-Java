package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.data.enums.PomodoroActionType;

public record PomodoroWebSocketResponse(Integer type, String remainingTime, Long timerId,Long sequenceId) {
	public static PomodoroWebSocketResponse of(PomodoroActionType type,String time,Long timerId,Long sequenceId) {
		return new PomodoroWebSocketResponse(type.getValue(),time,timerId,sequenceId);
	}
	public static PomodoroWebSocketResponse invalid(String message) {
		return new PomodoroWebSocketResponse(PomodoroActionType.INVALID.getValue(),message,null,null);
	}
}
