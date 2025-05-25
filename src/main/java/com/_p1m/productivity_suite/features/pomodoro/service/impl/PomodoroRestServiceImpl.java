package com._p1m.productivity_suite.features.pomodoro.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.data.models.Timer;
import com._p1m.productivity_suite.data.models.TimerSequence;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroRestResponse;
import com._p1m.productivity_suite.features.pomodoro.service.PomodoroRestService;
import com._p1m.productivity_suite.features.sequence.dto.SequenceResponse;
import com._p1m.productivity_suite.features.sequence.repository.SequenceRepository;
import com._p1m.productivity_suite.features.timer.dto.TimerResponse;
import com._p1m.productivity_suite.features.timer.repository.TimerRepository;
import com._p1m.productivity_suite.features.timerSequence.dto.TimerSequenceResponse;
import com._p1m.productivity_suite.features.timerSequence.repository.TimerSequenceRepository;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.utils.UserUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PomodoroRestServiceImpl implements PomodoroRestService {
	private final UserUtil userUtil;
	private final SequenceRepository sequenceRepository;
	private final TimerSequenceRepository timerSequenceRepository;
	private final TimerRepository timerRepository;

	@Override
	public PomodoroRestResponse retrieveDataByEmailAndStatus(String authHeader) {
		final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
		String email = userDto.getEmail();
		Sequence sequence = sequenceRepository.findStatusSequencesByUserEmail(email).orElseThrow(
				() -> new EntityNotFoundException("Current running Sequence not found with email: " + email));
		TimerSequence timerSequence = timerSequenceRepository.findTimerSequenceWithMaxStepBySequenceId(sequence.getId())
				.orElseThrow(() -> new EntityNotFoundException(
						"TimerSequence not found for sequence ID: " + sequence.getId()));
		Timer timer = timerSequence.getTimer();
		if (timer == null) {
			throw new IllegalStateException("Timer is missing");
		}
		return pomodoroRestResponse(
				new SequenceResponse(sequence.getId(), sequence.getType(), sequence.getDescription(),
						sequence.isStatus()),
				new TimerSequenceResponse(timerSequence.getStep()),
				new TimerResponse(timer.getId(), timer.getDuration(), timer.getRemainingTime(), timer.getTimerType()));
	}

	@Transactional
	@Override
	public void deleteData(Long id) {
	    List<TimerSequence> timerSequences = timerSequenceRepository.findAllBySequenceId(id);

	    List<Long> timerIds = timerSequences.stream()
	        .map(ts -> ts.getTimer().getId())
	        .collect(Collectors.toList());

	    timerSequenceRepository.deleteAll(timerSequences);

	    sequenceRepository.deleteById(id);

	    for (Long timerId : timerIds) {
	            timerRepository.deleteById(timerId);
	    }
	}

	private PomodoroRestResponse pomodoroRestResponse(final SequenceResponse sequence,
			final TimerSequenceResponse timerSequence, final TimerResponse timer) {
		return new PomodoroRestResponse(sequence, timerSequence, timer);
	}

}
