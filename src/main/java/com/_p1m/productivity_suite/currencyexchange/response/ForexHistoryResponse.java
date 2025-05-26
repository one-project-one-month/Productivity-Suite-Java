package com._p1m.productivity_suite.currencyexchange.response;

import java.util.Map;

public record ForexHistoryResponse (
        String info,
        String description,
        Map<String, String> rates
) {}
