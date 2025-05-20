package com._p1m.productivity_suite.features.todolist.service;

import com._p1m.productivity_suite.features.todolist.dto.TodoListRequest;
import com._p1m.productivity_suite.features.todolist.dto.TodoListResponse;

import java.util.List;

public interface TodoListService {
    void createTodoList(final String authHeader, final TodoListRequest todoListRequest);
    List<TodoListResponse> retrieveAll(final String authHeader);
    TodoListResponse retrieveOne(final Long id);
    void updateTodoList(final Long id, final TodoListRequest todoListRequest);
    void deleteTodoList(final Long id);
}
