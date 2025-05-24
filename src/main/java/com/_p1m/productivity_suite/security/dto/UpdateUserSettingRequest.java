package com._p1m.productivity_suite.security.dto;

import com._p1m.productivity_suite.config.annotations.ValidDateFormat;

public record UpdateUserSettingRequest(
        @ValidDateFormat String dateFormat,
        Long currencyId
) {}
