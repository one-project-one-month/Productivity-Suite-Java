package com._p1m.productivity_suite.features.sequence.service;

import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.features.sequence.dto.NewSequenceRequest;

public interface SequenceService {
	Sequence createSequence(String authHeader,NewSequenceRequest sequenceRequest);
	Sequence findSequenceById(Long id);
	void setStatusById(Long id,boolean status);
}
