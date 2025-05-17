package com._p1m.productivity_suite.features.sequence.service.impl;

import static com._p1m.productivity_suite.config.utils.RepositoryUtils.findByIdOrThrow;

import org.springframework.stereotype.Service;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.sequence.dto.SequenceRequest;
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
	private final CategoryRepository categoryRepository;
	private final UserUtil userUtil;
	
	@Transactional
	@Override
	public Sequence createSequence(final String authHeader, final SequenceRequest sequenceRequest) {
		final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
		final User user = findByIdOrThrow(this.userRepository, userDto.getId(), "User");
		final Category category = findByIdOrThrow(this.categoryRepository, sequenceRequest.categoryId(), "Category");
		final Sequence sequence = Sequence.builder().user(user).category(category).build();
		PersistenceUtils.save(this.sequenceRepository, sequence, "Sequence");
		return sequence;
	}

}
