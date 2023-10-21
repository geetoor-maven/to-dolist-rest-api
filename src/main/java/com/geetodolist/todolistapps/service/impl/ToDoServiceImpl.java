package com.geetodolist.todolistapps.service.impl;

import com.geetodolist.todolistapps.constant.ProgressTodoEnum;
import com.geetodolist.todolistapps.dto.Response;
import com.geetodolist.todolistapps.dto.todos.RequestToDo;
import com.geetodolist.todolistapps.dto.todos.RequestUpdateToDo;
import com.geetodolist.todolistapps.entity.ToDos;
import com.geetodolist.todolistapps.exception.ResourceNotFoundException;
import com.geetodolist.todolistapps.repository.ToDoRepository;
import com.geetodolist.todolistapps.service.ToDoService;
import com.geetodolist.todolistapps.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        theTodo.setTodoProgress(request.getProgressTodo());
        theTodo.setUsers(userService.getUserLogin());

        toDoRepository.save(theTodo);

        theResponse.setStatus(HttpStatus.CREATED.value());
        theResponse.setData(theTodo);
        return theResponse;
    }

    @Override
    public Response updateTodo(String todoId, RequestUpdateToDo request) {
        Response theResponse = new Response();

        ToDos theTodo = getTodoByTodoId(todoId);
        theTodo.setTitleTodo(request.getTitleTodo() != null ? request.getTitleTodo() : theTodo.getTitleTodo());
        theTodo.setDescTodo(request.getDescTodo() != null ? request.getDescTodo() : theTodo.getDescTodo());

        toDoRepository.save(theTodo);
        theResponse.setStatus(HttpStatus.OK.value());
        theResponse.setData(theTodo);
        return theResponse;
    }

    @Override
    public Response updateInProgressTodo(String todoId, String progress) {
        Response theResponse = new Response();

        ToDos theTodo = getTodoByTodoId(todoId);
        theTodo.setTodoProgress(progress);

        toDoRepository.save(theTodo);

        theResponse.setStatus(HttpStatus.OK.value());
        theResponse.setData(theTodo);
        return theResponse;
    }

    @Override
    public Response findTodoByTodoId(String todoId) {
        Response theResponse = new Response();
        Optional<ToDos> toDo = toDoRepository.findByUsersAndTodoId(userService.getUserLogin(), todoId);
        if (toDo.isPresent()){
            theResponse.setStatus(HttpStatus.FOUND.value());
            theResponse.setData(toDo);
            return theResponse;
        }
        throw new ResourceNotFoundException("Todo not found");
    }

    @Override
    public Page<ToDos> findAllTodosByUserLogIn(Pageable page) {
        return toDoRepository.findByUsers(userService.getUserLogin(), page);
    }

    @Override
    public ToDos getTodoByTodoId(String todoId) {
        Optional<ToDos> toDo = toDoRepository.findByUsersAndTodoId(userService.getUserLogin(), todoId);
        if (toDo.isPresent()){
            return toDo.get();
        }
        throw new ResourceNotFoundException("Todo not found");
    }

    @Override
    public void deleteTodoByTodoId(String todoId) {
        ToDos toDo = getTodoByTodoId(todoId);
        toDoRepository.delete(toDo);
    }

}
