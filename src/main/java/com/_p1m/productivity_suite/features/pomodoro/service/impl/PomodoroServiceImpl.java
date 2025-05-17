package com._p1m.productivity_suite.features.pomodoro.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.data.enums.PomodoroActionType;
import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.data.models.Timer;
import com._p1m.productivity_suite.features.pomodoro.domain.notification.PomodoroNotifier;
import com._p1m.productivity_suite.features.pomodoro.domain.session.PomodoroSession;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResponse;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResumeRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroStartRequest;
import com._p1m.productivity_suite.features.pomodoro.service.PomodoroService;
import com._p1m.productivity_suite.features.sequence.service.SequenceService;
import com._p1m.productivity_suite.features.timer.service.TimerService;
import com._p1m.productivity_suite.features.timerSequence.service.TimerSequenceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PomodoroServiceImpl implements PomodoroService {
	private final SequenceService sequenceService;
	private final TimerService timerService;
	private final TimerSequenceService timerSequenceService;
	private final Map<String, PomodoroSession> runningTasks = new ConcurrentHashMap<>();
	private final PomodoroNotifier pomodoroNotifier;

	@Override
	public PomodoroResponse timerStart(String user, PomodoroStartRequest request, String token) {
		if (runningTasks.containsKey(user)) {
			return new PomodoroResponse();
		}
		Sequence sequence = sequenceService.createSequence(token, request.sequenceRequest());
		Timer timer = timerService.createTimer(token, request.timerRequest());
		timerSequenceService.createTimerSequence(sequence, timer, request.timerSequenceRequest());
		return startCountdown(user, timer.getId(), timer.getDuration(), sequence.getId());
	}

	@Override
	public PomodoroResponse timerResume(String user, PomodoroResumeRequest request) {
		PomodoroSession existingSession = runningTasks.get(user);
		if (existingSession != null) {
			existingSession.task().cancel(false);
			existingSession.executor().shutdown();
			runningTasks.remove(user);
		}
		return startCountdown(user, request.getTimerId(), request.getRemainingTime(), null);
	}

	@Override
	public PomodoroResponse timerStop(String user) {
		PomodoroSession session = runningTasks.get(user);
		if (session != null) {
			session.task().cancel(false);
			session.executor().shutdown();
			long remainingTime = session.remainingTime().get();
			log.info("Stop to timer id {}", session.timerId());
			Timer timer = timerService.updateRemainingTime(session.timerId(), remainingTime);
			return new PomodoroResponse(PomodoroActionType.STOP, formatTime(remainingTime), timer.getId());
		}
		return new PomodoroResponse();
	}

	@Override
	public PomodoroResponse timerReset(String user) {
		PomodoroSession session = runningTasks.get(user);
		if (session != null) {
			session.task().cancel(false);
			session.executor().shutdown();
			Timer timer = timerService.resetRemainingTime(session.timerId());
			log.info("Remaining time after reset {}", timer.getRemainingTime());
			session.remainingTime().set(timer.getDuration());
			return new PomodoroResponse(PomodoroActionType.RESET, formatTime(timer.getRemainingTime()),
					timer.getId());
		}
		return new PomodoroResponse();
	}

	@Override
	public void deactivatePomodoroSession(String user) {
		timerStop(user);
		runningTasks.remove(user);
	}

	private PomodoroResponse startCountdown(String user, Long timerId, long timeLeftSeconds, Long sequenceId) {
		AtomicLong timeLeft = new AtomicLong(timeLeftSeconds);
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		ScheduledFuture<?> task = executor.scheduleAtFixedRate(() -> {
			long current = timeLeft.decrementAndGet();
			if (current > 0) {
				pomodoroNotifier.notifyTick(user, formatTime(current), timerId);
			} else {
				pomodoroNotifier.notifyComplete(user, timerId);
				PomodoroSession session = runningTasks.remove(user);
				if (session != null) {
					session.task().cancel(false);
					session.executor().shutdown();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

		runningTasks.put(user, new PomodoroSession(executor, task, timeLeft, timerId, sequenceId));
		return new PomodoroResponse(PomodoroActionType.TICK, formatTime(timeLeft.get()), timerId);
	}

	private String formatTime(long totalSeconds) {
		long minutes = totalSeconds / 60;
		long seconds = totalSeconds % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}
}
