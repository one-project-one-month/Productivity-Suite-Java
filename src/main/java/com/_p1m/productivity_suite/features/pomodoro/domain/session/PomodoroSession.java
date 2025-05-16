package com._p1m.productivity_suite.features.pomodoro.domain.session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PomodoroSession {
	ScheduledExecutorService executor;
	ScheduledFuture<?> task;
	AtomicLong remainingTime;
}
