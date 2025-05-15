package com._p1m.productivity_suite.features.categories.dto;

import com._p1m.productivity_suite.config.annotations.ValidCategoryType;
import com._p1m.productivity_suite.config.annotations.ValidName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @ValidName
    private String name;

    @ValidCategoryType
    private String description;

    @ValidCategoryType
    private Integer type;
}
