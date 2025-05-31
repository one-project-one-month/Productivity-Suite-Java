package com._p1m.productivity_suite.features.timer.dto;

import com._p1m.productivity_suite.config.annotations.PomodoroTimerFormat;

public record TimerRequest(
		@PomodoroTimerFormat Long duration, 
		@PomodoroTimerFormat Long remainingTime,
		Integer timerType) {
}
