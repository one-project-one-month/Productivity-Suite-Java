package com._p1m.productivity_suite.features.timer.dto;

import com._p1m.productivity_suite.config.annotations.PomodoroTimerFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimerRequest {
	@PomodoroTimerFormat
	Long duration;
	@PomodoroTimerFormat
	Long remainingTime;
	Integer timerType;
}
