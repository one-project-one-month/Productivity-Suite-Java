package com._p1m.productivity_suite.features.todolist.dto;

import com._p1m.productivity_suite.config.annotations.*;

public record TodoListRequest(
        @ValidTodoListTitle String title,
        @ValidTodoListDescription String description,
        @ValidStatusType Integer status,
        @ValidPriorityType Integer priority,
        @ValidTodoListCompletedAt Long completedAt,
        @ValidTodoListDueAt Long dueAt
) {}
