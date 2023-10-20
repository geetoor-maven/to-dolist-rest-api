package com.geetodolist.todolistapps.controller;

import com.geetodolist.todolistapps.dto.Response;
import com.geetodolist.todolistapps.dto.todos.RequestToDo;
import com.geetodolist.todolistapps.dto.todos.RequestUpdateToDo;
import com.geetodolist.todolistapps.entity.ToDos;
import com.geetodolist.todolistapps.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoController {
    private ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    // end point for create to - do
    @PostMapping(
            path = "/todo",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response> createToDo(@Valid @RequestBody RequestToDo toDo){
        Response theResponse = toDoService.createTodo(toDo);

        return ResponseEntity
                .status(theResponse.getStatus())
                .body(theResponse);
    }

    @PutMapping(
            path = "/todo/{todoId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response> updateTodo(@PathVariable("todoId")String todoId, @Valid @RequestBody RequestUpdateToDo request){
        Response theResponse = toDoService.updateTodo(todoId, request);

        return ResponseEntity
                .status(theResponse.getStatus())
                .body(theResponse);
    }

    @GetMapping(
            path = "/todos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ToDos> findAllTodosByUserLogIn(Pageable page){
        return toDoService.findAllTodosByUserLogIn(page).toList();
    }

    @GetMapping(
            path = "/todo/{todoId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response> findTodoByTodoId(@PathVariable("todoId") String todoId){
        Response theResponse = toDoService.findTodoByTodoId(todoId);

        return ResponseEntity
                .status(theResponse.getStatus())
                .body(theResponse);
    }

    @DeleteMapping(
            path = "/todo/{todoId}"
    )
    public void deleteTodoByTodoId(@PathVariable("todoId") String todoId){
        toDoService.deleteTodoByTodoId(todoId);
    }

}
