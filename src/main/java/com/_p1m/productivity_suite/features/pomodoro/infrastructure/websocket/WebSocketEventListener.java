package com._p1m.productivity_suite.features.pomodoro.infrastructure.websocket;

import java.security.Principal;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com._p1m.productivity_suite.features.pomodoro.service.PomodoroService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
	private final PomodoroService pomodoroService;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		Principal user = accessor.getUser();
		pomodoroService.deactivatePomodoroSession(user.getName());
		log.info("WebSocket Disconnected. Username: {}", user.getName());

	}
}
