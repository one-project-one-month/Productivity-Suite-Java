package com._p1m.productivity_suite.features.pomodoro.domain.notification;

public interface PomodoroNotifier {
	void notifyTick(String user, String timeLeft,Long timerId,Long sequenceId);
	void notifyComplete(String user,Long timerId,Long sequenceId);
}

