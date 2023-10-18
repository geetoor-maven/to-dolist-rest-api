package com.geetodolist.todolistapps.service;

import com.geetodolist.todolistapps.dto.Response;
import com.geetodolist.todolistapps.dto.todos.RequestToDo;
import com.geetodolist.todolistapps.entity.ToDos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToDoService {
    Response createTodo(RequestToDo request);
    Page<ToDos> findAllTodosByUserLogIn(Pageable page);
    ToDos findTodoById(String todoId);
    void deleteTodoByTodoId(String todoId);
}
