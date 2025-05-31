package com._p1m.productivity_suite.security.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record SetAmountUpdateRequest(@NotNull BigDecimal amount) {

}
