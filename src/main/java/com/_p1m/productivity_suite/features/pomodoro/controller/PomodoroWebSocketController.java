package com._p1m.productivity_suite.features.pomodoro.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResetRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResumeRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroStartRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroWebSocketResponse;
import com._p1m.productivity_suite.features.pomodoro.infrastructure.websocket.WebSocketResponseSender;
import com._p1m.productivity_suite.features.pomodoro.service.PomodoroWebSocketService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PomodoroWebSocketController {

	private final WebSocketResponseSender responseSender;
	private final PomodoroWebSocketService pomodoroWebSocketService;
	

	@MessageMapping("/pomodoro/start")
	public void startPomodoro(Principal principal, @Payload PomodoroStartRequest request,SimpMessageHeaderAccessor accessor) {
		String user = principal.getName();
		String token = (String) accessor.getSessionAttributes().get("token");
		PomodoroWebSocketResponse response = pomodoroWebSocketService.timerStart(user, request,token);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}
	
	@MessageMapping("/pomodoro/resume")
	public void resumePomodoro(Principal principal, @Payload PomodoroResumeRequest request,SimpMessageHeaderAccessor accessor) {
		String user = principal.getName();
		PomodoroWebSocketResponse response = pomodoroWebSocketService.timerResume(user, request);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}

	@MessageMapping("/pomodoro/stop")
	public void stopPomodoro(Principal principal) {
		String user = principal.getName();
		PomodoroWebSocketResponse response = pomodoroWebSocketService.timerStop(user);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}
	@MessageMapping("/pomodoro/reset")
	public void resetPomodoro(Principal principal,@Payload PomodoroResetRequest request) {
		String user = principal.getName();
		PomodoroWebSocketResponse response = pomodoroWebSocketService.timerReset(user,request);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}
	
	
}
