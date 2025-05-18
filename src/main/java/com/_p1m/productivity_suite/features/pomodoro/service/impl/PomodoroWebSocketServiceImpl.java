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
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResetRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResumeRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroStartRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroWebSocketResponse;
import com._p1m.productivity_suite.features.pomodoro.service.PomodoroWebSocketService;
import com._p1m.productivity_suite.features.sequence.dto.ExistingSequenceRequest;
import com._p1m.productivity_suite.features.sequence.dto.NewSequenceRequest;
import com._p1m.productivity_suite.features.sequence.service.SequenceService;
import com._p1m.productivity_suite.features.timer.service.TimerService;
import com._p1m.productivity_suite.features.timerSequence.dto.TimerSequenceRequest;
import com._p1m.productivity_suite.features.timerSequence.service.TimerSequenceService;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PomodoroWebSocketServiceImpl implements PomodoroWebSocketService {
	private final SequenceService sequenceService;
	private final TimerService timerService;
	private final TimerSequenceService timerSequenceService;
	private final Map<String, PomodoroSession> runningTasks = new ConcurrentHashMap<>();
	private final PomodoroNotifier pomodoroNotifier;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

	@Override
	public PomodoroWebSocketResponse timerStart(String user, PomodoroStartRequest request, String token) {
		if (runningTasks.containsKey(user)) {
			return PomodoroWebSocketResponse.invalid("Time is already running");
		}

		Sequence sequence;
		Timer timer = timerService.createTimer(token, request.timerRequest());

		if (request.sequenceRequest() instanceof ExistingSequenceRequest existing) {
			sequence = sequenceService.findSequenceById(existing.sequenceId());
			int nextStep = timerSequenceService.incrementNextStepBySequenceId(sequence.getId());
			timerSequenceService.createTimerSequence(sequence, timer, new TimerSequenceRequest(nextStep));
		} else if (request.sequenceRequest() instanceof NewSequenceRequest newSequence) {
			sequence = sequenceService.createSequence(token, newSequence);
			timerSequenceService.createTimerSequence(sequence, timer, request.timerSequenceRequest());
		} else {
			return PomodoroWebSocketResponse.invalid("Invalid sequence request");
		}

		return startCountdown(user, timer.getId(), timer.getDuration(), sequence.getId());

	}

	@Override
	public PomodoroWebSocketResponse timerResume(String user, PomodoroResumeRequest request) {
		PomodoroSession session = runningTasks.get(user);
		if (session != null) {
			session.task().cancel(false);
			runningTasks.remove(user);
		}
		return startCountdown(user, request.timerId(), request.remainingTime(), request.sequenceId());
	}

	@Override
	public PomodoroWebSocketResponse timerStop(String user) {
		PomodoroSession session = runningTasks.get(user);
		if (session != null) {
			session.task().cancel(false);
			long remainingTime = session.remainingTime().get();
			log.info("Stop to timer id {}", session.timerId());
			Timer timer = timerService.updateRemainingTime(session.timerId(), remainingTime);
			return PomodoroWebSocketResponse.of(PomodoroActionType.STOP, formatTime(remainingTime), timer.getId());
		}
		return PomodoroWebSocketResponse.invalid("Currently, no time is running.");
	}

	@Override
	public PomodoroWebSocketResponse timerReset(String user,PomodoroResetRequest request) {
		PomodoroSession session = runningTasks.get(user);
		if (session != null) {
			session.task().cancel(false);
			Timer timer = timerService.resetRemainingTime(session.timerId());
			log.info("Remaining time after reset: {}", timer.getRemainingTime());
			session.remainingTime().set(timer.getDuration());
			return PomodoroWebSocketResponse.of(PomodoroActionType.RESET, formatTime(timer.getRemainingTime()),
					timer.getId());
		}else {
			log.info("Requesting id to reset timer: {}",request.timerId());
			Timer timer = timerService.resetRemainingTime(request.timerId());
			return PomodoroWebSocketResponse.of(PomodoroActionType.RESET, formatTime(timer.getRemainingTime()),
					timer.getId());
		}
	}

	@Override
	public void deactivatePomodoroSession(String user) {
		timerStop(user);
		runningTasks.remove(user);
	}

	private PomodoroWebSocketResponse startCountdown(String user, Long timerId, long timeLeftSeconds, Long sequenceId) {
		AtomicLong timeLeft = new AtomicLong(timeLeftSeconds);

		ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(() -> {
			long current = timeLeft.decrementAndGet();
			if (current > 0) {
				pomodoroNotifier.notifyTick(user, formatTime(current), timerId);
			} else {
				pomodoroNotifier.notifyComplete(user, timerId);
				int currentStep = timerSequenceService.retrieveStepByTimerId(timerId);
				if(currentStep == 7) {
					sequenceService.setStatusById(sequenceId,true);
				}
				PomodoroSession session = runningTasks.remove(user);
				if (session != null) {
					session.task().cancel(false);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

		runningTasks.put(user, new PomodoroSession(scheduler, task, timeLeft, timerId, sequenceId));
		return PomodoroWebSocketResponse.of(PomodoroActionType.TICK, formatTime(timeLeft.get()), timerId);
	}

	private String formatTime(long totalSeconds) {
		long minutes = totalSeconds / 60;
		long seconds = totalSeconds % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}

	@PreDestroy
	public void shutdownScheduler() {
		scheduler.shutdownNow();
	}
}
