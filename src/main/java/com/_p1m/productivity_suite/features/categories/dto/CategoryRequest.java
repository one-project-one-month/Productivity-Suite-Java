package com._p1m.productivity_suite.features.categories.dto;

import com._p1m.productivity_suite.config.annotations.ValidCategoryDescription;
import com._p1m.productivity_suite.config.annotations.ValidCategoryType;
import com._p1m.productivity_suite.config.annotations.ValidName;

public record CategoryRequest(
        @ValidName String name,
        @ValidCategoryDescription String description,
        @ValidCategoryType Integer type
) {}
