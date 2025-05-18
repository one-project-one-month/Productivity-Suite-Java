package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.config.annotations.PomodoroTimerFormat;

public record PomodoroResumeRequest(@PomodoroTimerFormat Long remainingTime, Long timerId,Long sequenceId) {

}
