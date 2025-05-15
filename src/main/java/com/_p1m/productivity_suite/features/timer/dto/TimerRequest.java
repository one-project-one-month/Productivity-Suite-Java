package com._p1m.productivity_suite.features.timer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimerRequest {
	private Long duration;
	private Long remainingTime;
	private boolean resume;
	private Integer timerType;
	private String categoryName;
	private String description;
}
