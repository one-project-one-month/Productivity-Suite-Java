package com._p1m.productivity_suite.features.timer.service;

import com._p1m.productivity_suite.features.timer.dto.TimerRequest;
import com._p1m.productivity_suite.features.timer.dto.TimerResponse;

public interface TimerService {
	TimerResponse timerStart(String user, TimerRequest pomodoroRequest);
	TimerResponse timerStop(String user);
	void timerReset(String user);
}
