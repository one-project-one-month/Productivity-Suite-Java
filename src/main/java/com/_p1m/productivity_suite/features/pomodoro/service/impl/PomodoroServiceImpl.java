package com._p1m.productivity_suite.features.pomodoro.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.features.pomodoro.domain.notification.PomodoroNotifier;
import com._p1m.productivity_suite.features.pomodoro.domain.session.PomodoroSession;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResponse;
import com._p1m.productivity_suite.features.pomodoro.service.PomodoroService;
import com._p1m.productivity_suite.features.sequence.service.SequenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PomodoroServiceImpl implements PomodoroService {
	private final SequenceService sequenceService;
	private final Map<String, PomodoroSession> runningTasks = new ConcurrentHashMap<>();
	private final PomodoroNotifier pomodoroNotifier;
	@Override
	public PomodoroResponse timerStart(String user, PomodoroRequest pomodoroRequest,String token) {
		if (runningTasks.containsKey(user)) {
			return new PomodoroResponse();
		}
		
		AtomicLong timeLeft = new AtomicLong(
				(pomodoroRequest.isResume()) ? pomodoroRequest.getRemainingTime() : pomodoroRequest.getDuration());

		ScheduledExecutorService userExecutor = Executors.newSingleThreadScheduledExecutor();

		ScheduledFuture<?> task = userExecutor.scheduleAtFixedRate(() -> {
			long current = timeLeft.decrementAndGet();
			if (current > 0) {
				pomodoroNotifier.notifyTick(user, current);
			} else {
				pomodoroNotifier.notifyComplete(user);
				PomodoroSession session = runningTasks.remove(user);
				if (session != null && session.getTask() != null) {
					session.getTask().cancel(false);
					session.getExecutor().shutdown();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

		runningTasks.put(user, new PomodoroSession(userExecutor, task, timeLeft));
		return new PomodoroResponse("Tick", String.valueOf(timeLeft.get()));
	}

	@Override
	public void timerReset(String user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PomodoroResponse timerStop(String user) {
		PomodoroSession session = runningTasks.remove(user);
		if (session != null) {
			session.getTask().cancel(false);
			session.getExecutor().shutdown();
			long remainingTime = session.getRemainingTime().get();
			return new PomodoroResponse("Stop", String.valueOf(remainingTime));
		}else {
			return new PomodoroResponse();
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
