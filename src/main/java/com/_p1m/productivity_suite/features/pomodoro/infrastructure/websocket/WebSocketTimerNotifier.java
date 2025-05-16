package com._p1m.productivity_suite.features.pomodoro.infrastructure.websocket;

import org.springframework.stereotype.Component;

import com._p1m.productivity_suite.features.pomodoro.domain.notification.PomodoroNotifier;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketTimerNotifier implements PomodoroNotifier{
	private final WebSocketResponseSender responseSender;
	@Override
	public void notifyTick(String user, long timeLeft) {
		responseSender.send(user, "Tick", String.valueOf(timeLeft));
	}

	@Override
	public void notifyComplete(String user) {
		responseSender.send(user, "Complete", "0");
	}

}
