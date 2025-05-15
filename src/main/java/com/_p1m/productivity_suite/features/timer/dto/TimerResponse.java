package com._p1m.productivity_suite.features.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimerResponse {
	private String type;
	private String remainingTime;
}
