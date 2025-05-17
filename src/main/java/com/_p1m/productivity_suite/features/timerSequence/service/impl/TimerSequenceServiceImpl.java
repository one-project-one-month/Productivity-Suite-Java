package com._p1m.productivity_suite.features.timerSequence.service.impl;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.data.models.Timer;
import com._p1m.productivity_suite.data.models.TimerSequence;
import com._p1m.productivity_suite.features.timerSequence.dto.TimerSequenceRequest;
import com._p1m.productivity_suite.features.timerSequence.repository.TimerSequenceRepository;
import com._p1m.productivity_suite.features.timerSequence.service.TimerSequenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerSequenceServiceImpl implements TimerSequenceService{
	private final TimerSequenceRepository timerSequenceRepository;
	@Override
	public void createTimerSequence(Sequence sequence, Timer timer,TimerSequenceRequest timerSequenceRequest) {
		final TimerSequence timerSeq = TimerSequence.builder()
				.sequence(sequence)
				.timer(timer)
				.step(timerSequenceRequest.step())
				.build();
		PersistenceUtils.save(this.timerSequenceRepository, timerSeq, "TimerSequence");
	}
	

}
