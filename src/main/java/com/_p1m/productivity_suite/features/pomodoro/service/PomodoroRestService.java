package com._p1m.productivity_suite.features.pomodoro.service;

import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroRestResponse;

public interface PomodoroRestService {

	PomodoroRestResponse retrieveDataByEmailAndStatus(String authHeader);

	void deleteData(Long id);

}
