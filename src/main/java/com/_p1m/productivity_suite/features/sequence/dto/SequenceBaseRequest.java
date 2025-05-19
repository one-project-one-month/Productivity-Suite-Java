package com._p1m.productivity_suite.features.sequence.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.EXISTING_PROPERTY,
	    property = "mode",
	    visible = true
	)
	@JsonSubTypes({
	    @JsonSubTypes.Type(value = NewSequenceRequest.class, name = "new"),
	    @JsonSubTypes.Type(value = ExistingSequenceRequest.class, name = "existing")
	})
	public interface SequenceBaseRequest {
	    String mode();
	}


