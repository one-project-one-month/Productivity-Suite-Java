package com._p1m.productivity_suite.features.pomodoro.domain.notification;

public interface PomodoroNotifier {
	void notifyTick(String user, long timeLeft);
	void notifyComplete(String user);
}

