package com._p1m.productivity_suite.features.pomodoro.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResponse;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResumeRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroStartRequest;
import com._p1m.productivity_suite.features.pomodoro.infrastructure.websocket.WebSocketResponseSender;
import com._p1m.productivity_suite.features.pomodoro.service.PomodoroService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PomodoroController {

	private final WebSocketResponseSender responseSender;
	private final PomodoroService pomodoroService;
	

	@MessageMapping("/pomodoro/start")
	public void startPomodoro(Principal principal, @Payload PomodoroStartRequest request,SimpMessageHeaderAccessor accessor) {
		String user = principal.getName();
		String token = (String) accessor.getSessionAttributes().get("token");
		PomodoroResponse response = pomodoroService.timerStart(user, request,token);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}
	
	@MessageMapping("/pomodoro/resume")
	public void startPomodoro(Principal principal, @Payload PomodoroResumeRequest request,SimpMessageHeaderAccessor accessor) {
		String user = principal.getName();
		PomodoroResponse response = pomodoroService.timerResume(user, request);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}

	@MessageMapping("/pomodoro/stop")
	public void stopPomodoro(Principal principal) {
		String user = principal.getName();
		PomodoroResponse response = pomodoroService.timerStop(user);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}
	@MessageMapping("/pomodoro/reset")
	public void resetPomodoro(Principal principal) {
		String user = principal.getName();
		PomodoroResponse response = pomodoroService.timerReset(user);
		responseSender.send(user, response.type(), response.remainingTime(),response.timerId());
	}
	
}
