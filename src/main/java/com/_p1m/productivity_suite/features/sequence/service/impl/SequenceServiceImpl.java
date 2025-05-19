package com._p1m.productivity_suite.features.sequence.service.impl;

import static com._p1m.productivity_suite.config.utils.RepositoryUtils.findByIdOrThrow;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.sequence.dto.NewSequenceRequest;
import com._p1m.productivity_suite.features.sequence.repository.SequenceRepository;
import com._p1m.productivity_suite.features.sequence.service.SequenceService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SequenceServiceImpl implements SequenceService {

	private final SequenceRepository sequenceRepository;
	private final UserRepository userRepository;
	private final UserUtil userUtil;

	@Transactional
	@Override
	public Sequence createSequence(final String authHeader, final NewSequenceRequest sequenceRequest) {
		final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
		final User user = findByIdOrThrow(this.userRepository, userDto.getId(), "User");
	        final Sequence sequence = Sequence.builder()
	                .user(user)
	                .type(sequenceRequest.type())
	                .status(sequenceRequest.status())
	                .description(sequenceRequest.description())
	                .build();
	        PersistenceUtils.save(this.sequenceRepository, sequence, "Sequence");
	        return sequence;
	}
	
	public Sequence findSequenceById(Long id) {
		Sequence sequence = findByIdOrThrow(this.sequenceRepository,id,"Sequence");
		return sequence;
	}

	@Override
	public void setStatusById(Long id,boolean status) {
		Sequence sequence = findByIdOrThrow(this.sequenceRepository, id, "Sequence");
		sequence.setStatus(status);
		PersistenceUtils.save(this.sequenceRepository, sequence, "Sequence");
	}

}

