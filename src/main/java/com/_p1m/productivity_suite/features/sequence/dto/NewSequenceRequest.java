package com._p1m.productivity_suite.features.sequence.dto;

public record NewSequenceRequest(int type, String description, boolean status) implements SequenceBaseRequest {

	@Override
	public String mode() {
		return "new";
	}
}
