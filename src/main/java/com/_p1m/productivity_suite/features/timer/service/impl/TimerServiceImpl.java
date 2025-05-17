package com._p1m.productivity_suite.features.timer.service.impl;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.models.Timer;
import com._p1m.productivity_suite.features.timer.dto.TimerRequest;
import com._p1m.productivity_suite.features.timer.repository.TimerRepository;
import com._p1m.productivity_suite.features.timer.service.TimerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerServiceImpl implements TimerService{
	private final TimerRepository timerRepository;
	@Override
	public Timer createTimer(final String authHeader, final TimerRequest timerRequest) {
		Timer timer = Timer.builder()
                .duration(timerRequest.duration())
                .remainingTime(timerRequest.remainingTime())
                .timerType(timerRequest.timerType())
                .build();
		PersistenceUtils.save(this.timerRepository, timer, "Timer");
		return timer;
	}
	@Override
	public Timer updateRemainingTime(Long id,Long timerLeft) {
		Timer timer = RepositoryUtils.findByIdOrThrow(this.timerRepository, id, "Timer");
		timer.setRemainingTime(timerLeft);
		PersistenceUtils.save(this.timerRepository, timer, "Timer");
		return timer;
	}
	@Override
	public Timer resetRemainingTime(Long id) {
		final Timer timer = RepositoryUtils.findByIdOrThrow(this.timerRepository, id, "Timer");
		timer.setRemainingTime(timer.getDuration());
		PersistenceUtils.save(this.timerRepository, timer, "Timer");
		return timer;
	}

}
