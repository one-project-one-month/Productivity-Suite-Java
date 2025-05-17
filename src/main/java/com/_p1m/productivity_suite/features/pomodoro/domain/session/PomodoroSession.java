package com._p1m.productivity_suite.features.pomodoro.domain.session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

public record PomodoroSession(
		ScheduledExecutorService executor,
		ScheduledFuture<?> task,
		AtomicLong remainingTime,
	  	Long timerId,
		Long sequenceId
) {}
