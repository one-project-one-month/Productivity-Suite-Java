package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.features.sequence.dto.SequenceResponse;
import com._p1m.productivity_suite.features.timer.dto.TimerResponse;
import com._p1m.productivity_suite.features.timerSequence.dto.TimerSequenceResponse;

public record PomodoroRestResponse(
		SequenceResponse sequenceResponse,
		TimerSequenceResponse timerSequenceResponse,
		TimerResponse timerResponse
) {}
