package com.geetodolist.todolistapps.service;

import com.geetodolist.todolistapps.dto.Response;
import com.geetodolist.todolistapps.dto.todos.RequestToDo;

public interface ToDoService {
    Response createTodo(RequestToDo request);
}
