package com._p1m.productivity_suite.features.todolist.repository;

import com._p1m.productivity_suite.data.models.TodoList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findAllByUserId(Long userId, Sort sort);
}
