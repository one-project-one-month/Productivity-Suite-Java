package com._p1m.productivity_suite.config.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebSocketResponse {
	private int success;
    private int code;
    private Object data;
}
