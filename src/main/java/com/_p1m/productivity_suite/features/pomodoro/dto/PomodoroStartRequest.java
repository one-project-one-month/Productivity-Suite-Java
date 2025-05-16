package com._p1m.productivity_suite.features.pomodoro.dto;

import com._p1m.productivity_suite.features.sequence.dto.SequenceRequest;
import com._p1m.productivity_suite.features.timer.dto.TimerRequest;
import com._p1m.productivity_suite.features.timerSequence.dto.TimerSequenceRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PomodoroStartRequest {
	private TimerRequest timerRequest;
	private SequenceRequest sequenceRequest;
	private TimerSequenceRequest timerSequenceRequest;
}
