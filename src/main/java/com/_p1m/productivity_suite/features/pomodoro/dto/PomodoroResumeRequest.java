package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.config.annotations.PomodoroTimerFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PomodoroResumeRequest {
	@PomodoroTimerFormat
	private Long remainingTime;
	private Long timerId;
}
