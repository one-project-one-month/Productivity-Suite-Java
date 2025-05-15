package com._p1m.productivity_suite.features.timer.infrastructure.websocket;

import org.springframework.stereotype.Component;

import com._p1m.productivity_suite.features.timer.domain.notification.TimerNotifier;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketTimerNotifier implements TimerNotifier{
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
