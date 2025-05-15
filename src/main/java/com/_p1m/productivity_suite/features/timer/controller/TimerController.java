package com._p1m.productivity_suite.features.timer.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com._p1m.productivity_suite.features.timer.dto.TimerRequest;
import com._p1m.productivity_suite.features.timer.dto.TimerResponse;
import com._p1m.productivity_suite.features.timer.infrastructure.websocket.WebSocketResponseSender;
import com._p1m.productivity_suite.features.timer.service.TimerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TimerController {

	private final WebSocketResponseSender responseSender;
	private final TimerService timerService;
	

	@MessageMapping("/pomodoro/start")
	public void startPomodoro(Principal principal, @Payload TimerRequest request) {
		String user = principal.getName();
		TimerResponse response = timerService.timerStart(user, request);
		responseSender.send(user, response.getType(), response.getRemainingTime());
	}

	@MessageMapping("/pomodoro/stop")
	public void stopPomodoro(Principal principal) {
		String user = principal.getName();
		TimerResponse response = timerService.timerStop(user);
		responseSender.send(user, response.getType(), response.getRemainingTime());
	}
	@MessageMapping("/pomodoro/reset")
	public void resetPomodoro(Principal principal) {
//		timerService.timerReset(principal);
	}
	
}
