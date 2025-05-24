package com._p1m.productivity_suite.features.timerSequence.service;

import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.data.models.Timer;
import com._p1m.productivity_suite.features.timerSequence.dto.TimerSequenceRequest;

public interface TimerSequenceService {
	void createTimerSequence(Sequence sequence,Timer timer,TimerSequenceRequest timerSequenceRequest);
	Integer incrementNextStepBySequenceId(Long id);
	Integer retrieveStepByTimerId(Long id);
}
