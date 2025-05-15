package com._p1m.productivity_suite.features.timer.service.impl;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.features.timer.domain.notification.TimerNotifier;
import com._p1m.productivity_suite.features.timer.domain.session.PomodoroSession;
import com._p1m.productivity_suite.features.timer.dto.TimerRequest;
import com._p1m.productivity_suite.features.timer.dto.TimerResponse;
import com._p1m.productivity_suite.features.timer.service.TimerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerServiceImpl implements TimerService {
	
	private final Map<String, PomodoroSession> runningTasks = new ConcurrentHashMap<>();
	private final TimerNotifier timerNotifier;
	@Override
	public TimerResponse timerStart(String user, TimerRequest pomodoroRequest) {
		if (runningTasks.containsKey(user)) {
			return new TimerResponse();
		}

		AtomicLong timeLeft = new AtomicLong(
				(pomodoroRequest.isResume()) ? pomodoroRequest.getRemainingTime() : pomodoroRequest.getDuration());

		ScheduledExecutorService userExecutor = Executors.newSingleThreadScheduledExecutor();

		ScheduledFuture<?> task = userExecutor.scheduleAtFixedRate(() -> {
			long current = timeLeft.decrementAndGet();
			if (current > 0) {
				timerNotifier.notifyTick(user, current);
			} else {
				timerNotifier.notifyComplete(user);
				PomodoroSession session = runningTasks.remove(user);
				if (session != null && session.getTask() != null) {
					session.getTask().cancel(false);
					session.getExecutor().shutdown();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

		runningTasks.put(user, new PomodoroSession(userExecutor, task, timeLeft));
		return new TimerResponse("Tick", String.valueOf(timeLeft.get()));
	}

	@Override
	public void timerReset(String user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TimerResponse timerStop(String user) {
		PomodoroSession session = runningTasks.remove(user);
		if (session != null) {
			session.getTask().cancel(false);
			session.getExecutor().shutdown();
			long remainingTime = session.getRemainingTime().get();
			return new TimerResponse("Stop", String.valueOf(remainingTime));
		}else {
			return new TimerResponse();
		}
	}
//
//	@Override
//	public void timerReset(Principal principal) {
//		String user = principal.getName();
//
//		PomodoroSession session = runningTasks.remove(user);
//		if (session != null) {
//			session.getTask().cancel(false);
//		}
//
//		messagingTemplate.convertAndSendToUser(user, "/queue/pomodoro", "Pomodoro reset to 60 seconds.");
//	}

}
