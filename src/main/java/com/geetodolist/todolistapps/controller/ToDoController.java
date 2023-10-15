package com.geetodolist.todolistapps.controller;

import com.geetodolist.todolistapps.dto.Response;
import com.geetodolist.todolistapps.dto.todos.RequestToDo;
import com.geetodolist.todolistapps.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
