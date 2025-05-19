package com._p1m.productivity_suite.features.timer.service;

import com._p1m.productivity_suite.data.models.Timer;
import com._p1m.productivity_suite.features.timer.dto.TimerRequest;

public interface TimerService {

	Timer createTimer(final String authHeader, final TimerRequest timerRequest);
	Timer updateRemainingTime(final Long id,final Long timeLeft);
	Timer resetRemainingTime(final Long id);
}
