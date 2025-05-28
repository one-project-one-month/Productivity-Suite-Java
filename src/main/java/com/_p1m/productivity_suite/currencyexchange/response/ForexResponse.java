package com._p1m.productivity_suite.currencyexchange.response;

import java.util.Map;

public record ForexResponse(
        String info,
        String description,
        Map<String, String> rates
) {}
