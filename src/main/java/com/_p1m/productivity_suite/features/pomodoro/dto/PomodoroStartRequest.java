package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.features.sequence.dto.SequenceRequest;
import com._p1m.productivity_suite.features.timer.dto.TimerRequest;
import com._p1m.productivity_suite.features.timerSequence.dto.TimerSequenceRequest;

public record PomodoroStartRequest(
		TimerRequest timerRequest,
		SequenceRequest sequenceRequest,
		TimerSequenceRequest timerSequenceRequest
) {}
