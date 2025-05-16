package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.data.enums.PomodoroActionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PomodoroResponse {
	private PomodoroActionType type;
	private String remainingTime;
	private Long timerId;
}
