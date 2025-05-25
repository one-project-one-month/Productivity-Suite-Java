package com._p1m.productivity_suite.features.pomodoro.service;

import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResetRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroResumeRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroStartRequest;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroWebSocketResponse;

public interface PomodoroWebSocketService {
	PomodoroWebSocketResponse timerStart(String user, PomodoroStartRequest pomodoroRequest,String token);
	PomodoroWebSocketResponse timerResume(String user, PomodoroResumeRequest pomodoroRequest);
	PomodoroWebSocketResponse timerStop(String user);
	PomodoroWebSocketResponse timerReset(String user,PomodoroResetRequest pomodoroRequest);
	void deactivatePomodoroSession(String user);
}
