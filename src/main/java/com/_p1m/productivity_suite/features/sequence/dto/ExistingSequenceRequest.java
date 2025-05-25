package com._p1m.productivity_suite.features.sequence.dto;

public record ExistingSequenceRequest(Long sequenceId) implements SequenceBaseRequest {

	@Override
	public String mode() {
		return "existing";
	}
}
