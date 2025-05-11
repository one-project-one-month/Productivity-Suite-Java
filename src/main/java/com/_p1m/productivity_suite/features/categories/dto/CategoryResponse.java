package com._p1m.productivity_suite.features.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private boolean active;
    private Long createdAt;
    private Long updatedAt;
}
