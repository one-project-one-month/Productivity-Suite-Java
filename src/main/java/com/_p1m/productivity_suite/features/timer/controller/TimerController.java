package com._p1m.productivity_suite.features.timer.controller;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com._p1m.productivity_suite.features.timer.domain.PomodoroSession;
import com._p1m.productivity_suite.features.timer.dto.PomodoroRequest;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TimerController {

	private final SimpMessagingTemplate messagingTemplate;

	private final Map<String, PomodoroSession> runningTasks = new ConcurrentHashMap<>();

	@MessageMapping("/pomodoro/start")
	public void startPomodoro(Principal principal, @Payload PomodoroRequest pomodoroRequest) {
		String user = principal.getName();

		if (runningTasks.containsKey(user)) {
			messagingTemplate.convertAndSendToUser(user, "/queue/pomodoro", "Pomodoro already running.");
			return;
		}

		AtomicLong timeLeft = new AtomicLong(
				(pomodoroRequest.isResume()) ? pomodoroRequest.getRemainingTime() : pomodoroRequest.getDuration());

		ScheduledExecutorService userExecutor = Executors.newSingleThreadScheduledExecutor();

		ScheduledFuture<?> task = userExecutor.scheduleAtFixedRate(() -> {
			long current = timeLeft.decrementAndGet();
			if (current > 0) {
				messagingTemplate.convertAndSendToUser(user, "/queue/pomodoro", current);
			} else {
				messagingTemplate.convertAndSendToUser(user, "/queue/pomodoro", "Pomodoro complete!");
				PomodoroSession session = runningTasks.remove(user);
				if (session != null && session.getTask() != null) {
					session.getTask().cancel(false);
					session.getExecutor().shutdown();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

		runningTasks.put(user, new PomodoroSession(userExecutor, task, timeLeft));
		messagingTemplate.convertAndSendToUser(user, "/queue/pomodoro", "Pomodoro started.");
	}

	@MessageMapping("/pomodoro/stop")
	public void stopPomodoro(Principal principal) {
		String username = principal.getName();

		PomodoroSession session = runningTasks.remove(username);
		if (session != null) {
			session.getTask().cancel(false);
			session.getExecutor().shutdown();
			long remaining = session.getRemainingTime().get();
			messagingTemplate.convertAndSendToUser(username, "/queue/pomodoro",
					"Pomodoro stopped. Remaining Time" + remaining + " seconds.");
		} else {
			messagingTemplate.convertAndSendToUser(username, "/queue/pomodoro", "No Pomodoro is currently running.");
		}
	}

	@MessageMapping("/pomodoro/reset")
	public void resetPomodoro(Principal principal) {
		String username = principal.getName();

		PomodoroSession session = runningTasks.remove(username);
		if (session != null) {
			session.getTask().cancel(false);
		}

		messagingTemplate.convertAndSendToUser(username, "/queue/pomodoro", "Pomodoro reset to 60 seconds.");
	}

}
