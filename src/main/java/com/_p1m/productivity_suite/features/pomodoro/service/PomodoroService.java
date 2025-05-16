package com._p1m.productivity_suite.features.pomodoro.service;

import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResponse;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResumeRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroStartRequest;

public interface PomodoroService {
	PomodoroResponse timerStart(String user, PomodoroStartRequest pomodoroRequest,String token);
	PomodoroResponse timerResume(String user, PomodoroResumeRequest pomodoroRequest);
	PomodoroResponse timerStop(String user);
	PomodoroResponse timerReset(String user);
	void deactivatePomodoroSession(String user);
}
