package com._p1m.productivity_suite.features.pomodoro.infrastructure.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com._p1m.productivity_suite.config.response.dto.WebSocketResponse;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroWebSocketResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketResponseSender {
	private static final String POMODORO_ROUTE = "/queue/pomodoro";

	private final SimpMessagingTemplate messagingTemplate;

	public void send(String user, Integer type, String remainingTime,Long timerId,Long sequenceId) {
		
		WebSocketResponse response = WebSocketResponse.builder()
			.success(1)
			.code(200)
			.data(new PomodoroWebSocketResponse(type, remainingTime,timerId,sequenceId))
			.build();

		messagingTemplate.convertAndSendToUser(user, POMODORO_ROUTE, response);
	}
}
