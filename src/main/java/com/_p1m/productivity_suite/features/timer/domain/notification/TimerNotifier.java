package com._p1m.productivity_suite.features.timer.domain.notification;

public interface TimerNotifier {
	void notifyTick(String user, long timeLeft);
	void notifyComplete(String user);
}
