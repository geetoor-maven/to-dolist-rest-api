package com.geetodolist.todolistapps.service.impl;

import com.geetodolist.todolistapps.dto.Response;
import com.geetodolist.todolistapps.dto.todos.RequestToDo;
import com.geetodolist.todolistapps.entity.ToDos;
import com.geetodolist.todolistapps.repository.ToDoRepository;
import com.geetodolist.todolistapps.service.ToDoService;
import com.geetodolist.todolistapps.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository toDoRepository;
    private UserService userService;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, UserService userService) {
        this.toDoRepository = toDoRepository;
        this.userService = userService;
    }

    // function for create To-Do
    @Transactional
    @Override
    public Response createTodo(RequestToDo request) {

        Response theResponse = new Response();

        ToDos theTodo = new ToDos();
        theTodo.setTodoId(UUID.randomUUID().toString());
        theTodo.setTitleTodo(request.getTitleTodo());
        theTodo.setDescTodo(request.getDescTodo());
        theTodo.setUsers(userService.getUserLogin());

        toDoRepository.save(theTodo);

        theResponse.setStatus(HttpStatus.CREATED.value());
        theResponse.setData(theTodo);
        return theResponse;
    }
}
