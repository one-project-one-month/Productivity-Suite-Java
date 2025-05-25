package com._p1m.productivity_suite.features.todolist.service.impl;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;
import com._p1m.productivity_suite.data.enums.PriorityType;
import com._p1m.productivity_suite.data.enums.StatusType;
import com._p1m.productivity_suite.data.models.TodoList;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.todolist.dto.TodoListRequest;
import com._p1m.productivity_suite.features.todolist.dto.TodoListResponse;
import com._p1m.productivity_suite.features.todolist.repository.TodoListRepository;
import com._p1m.productivity_suite.features.todolist.service.TodoListService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoListServiceImpl implements TodoListService {

    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Override
    public void createTodoList(final String authHeader, final TodoListRequest todoListRequest) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");

        final TodoList todoList = new TodoList(
                todoListRequest.title(),
                todoListRequest.description(),
                todoListRequest.priority(),
                todoListRequest.status(),
                todoListRequest.completedAt(),
                todoListRequest.dueAt(),
                user
        );

        PersistenceUtils.save(this.todoListRepository, todoList, "TodoList");
    }

    @Override
    public List<TodoListResponse> retrieveAll(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        List<TodoList> todoLists = RepositoryUtils.findAllByUserId(userDto.getId(), sortByName, this.todoListRepository::findAllByUserId);
        return todoLists.stream()
                .map(this::toTodoListResponseWithType)
                .toList();
    }


    @Override
    public TodoListResponse retrieveOne(final Long id) {
        final TodoList todoList = RepositoryUtils.findByIdOrThrow(this.todoListRepository, id, "TodoList");
        return this.toTodoListResponseWithType(todoList);
    }

    @Override
    public void updateTodoList(final Long id, final TodoListRequest todoListRequest) {
        final TodoList todoList = RepositoryUtils.findByIdOrThrow(this.todoListRepository, id, "TodoList");
        todoList.setTitle(todoListRequest.title());
        todoList.setDescription(todoListRequest.description());
        todoList.setPriority(todoListRequest.priority());
        todoList.setStatus(todoListRequest.status());
        todoList.setCompletedAt(todoListRequest.completedAt());
        todoList.setDueAt(todoListRequest.dueAt());
        PersistenceUtils.save(this.todoListRepository, todoList, "TodoList");
    }

    @Override
    public void deleteTodoList(final Long id) {
        RepositoryUtils.findByIdOrThrow(this.todoListRepository, id, "TodoList");
        PersistenceUtils.deleteById(this.todoListRepository, id, "TodoList");
    }

    private TodoListResponse toTodoListResponseWithType(final TodoList todoList) {
        return new TodoListResponse(
                todoList.getId(),
                todoList.getTitle(),
                todoList.getDescription(),
                todoList.getStatus(),
                StatusType.fromInt(todoList.getStatus()).getCode(),
                todoList.getPriority(),
                PriorityType.fromInt(todoList.getPriority()).getCode(),
                todoList.getCompletedAt(),
                todoList.getDueAt(),
                todoList.getCreatedAt(),
                todoList.getUpdatedAt()
        );
    }
}