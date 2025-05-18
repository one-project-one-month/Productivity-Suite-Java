package com._p1m.productivity_suite.features.pomodoro.infrastructure.websocket;

import org.springframework.stereotype.Component;

import com._p1m.productivity_suite.data.enums.PomodoroActionType;
import com._p1m.productivity_suite.features.pomodoro.domain.notification.PomodoroNotifier;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketTimerNotifier implements PomodoroNotifier{
	private final WebSocketResponseSender responseSender;
	@Override
	public void notifyTick(String user, String timeLeft,Long timerId,Long sequenceId) {
		responseSender.send(user, PomodoroActionType.TICK.getValue(), timeLeft,timerId,sequenceId);
	}

	@Override
	public void notifyComplete(String user,Long timerId,Long sequenceId) {
		responseSender.send(user, PomodoroActionType.COMPLETE.getValue(), "00:00",timerId,sequenceId);
	}

}
