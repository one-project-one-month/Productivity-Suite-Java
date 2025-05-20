package com._p1m.productivity_suite.features.currency.dto;

import com._p1m.productivity_suite.config.annotations.ValidName;

public record CurrencyRequest(
        @ValidName String name
) {}
