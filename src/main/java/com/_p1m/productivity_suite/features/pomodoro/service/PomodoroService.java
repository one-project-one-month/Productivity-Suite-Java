package com._p1m.productivity_suite.features.pomodoro.service;

import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResponse;

public interface PomodoroService {
	PomodoroResponse timerStart(String user, PomodoroRequest pomodoroRequest,String token);
	PomodoroResponse timerStop(String user);
	void timerReset(String user);
}
