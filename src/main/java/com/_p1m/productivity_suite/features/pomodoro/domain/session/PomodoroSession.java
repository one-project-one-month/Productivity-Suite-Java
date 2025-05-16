package com._p1m.productivity_suite.features.pomodoro.domain.session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PomodoroSession {
	private final ScheduledExecutorService executor;
	private final ScheduledFuture<?> task;
	private final AtomicLong remainingTime;
	private final Long timerId;
	private final Long sequenceId;
}
