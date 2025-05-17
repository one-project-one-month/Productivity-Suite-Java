package com._p1m.productivity_suite.features.sequence.service;

import com._p1m.productivity_suite.data.models.Sequence;
import com._p1m.productivity_suite.features.sequence.dto.SequenceRequest;

public interface SequenceService {
	Sequence createSequence(String authHeader,SequenceRequest sequenceRequest);
}
