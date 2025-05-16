package com._p1m.productivity_suite.features.timer.service.impl;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import com._p1m.productivity_suite.data.models.Timer;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.timer.service.TimerService;
import com._p1m.productivity_suite.features.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimerServiceImpl implements TimerService{
	private final UserRepository userRepository;
	@Override
	public void createTimer(String userEmail) {
		final User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));
		Timer timer = Timer.builder()
				.duration((long) 100)
				.remainingTime((long)10).timerType(1).build();
	}

}
