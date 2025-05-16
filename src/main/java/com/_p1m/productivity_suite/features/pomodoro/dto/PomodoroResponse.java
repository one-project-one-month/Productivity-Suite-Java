package com._p1m.productivity_suite.features.pomodoro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PomodoroResponse {
	private String type;
	private String remainingTime;
}
