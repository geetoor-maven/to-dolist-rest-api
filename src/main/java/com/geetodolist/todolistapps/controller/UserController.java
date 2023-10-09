package com.geetodolist.todolistapps.controller;

import com.geetodolist.todolistapps.entity.Users;
import com.geetodolist.todolistapps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(
            path = "/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Users> findUser(){
        return new ResponseEntity<>(userService.readUserByUserId(), HttpStatus.OK);
    }
}
